package com.zxdesruc.budgettracker.service.impl;

import com.zxdesruc.budgettracker.model.EmailLog;
import com.zxdesruc.budgettracker.repository.EmailLogRepository;
import com.zxdesruc.budgettracker.service.EmailSyncService;
import com.zxdesruc.budgettracker.util.EmailContentExtractor;
import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.search.FromStringTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;

@Service
public class EmailSyncServiceImpl implements EmailSyncService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSyncServiceImpl.class);

    private final EmailLogRepository emailLogRepository;

    @Value("${mail.imap.host}")
    private String imapHost;

    @Value("${mail.imap.port}")
    private int imapPort;

    @Value("${mail.imap.ssl.enable}")
    private boolean imapSsl;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.bank.sender}")
    private String bankEmailSender;

    public EmailSyncServiceImpl(EmailLogRepository emailLogRepository) {
        this.emailLogRepository = emailLogRepository;
    }

    @Override
    @Transactional
    public void syncEmails() {
        logger.info("Начинаем синхронизацию писем с почты");

        Properties props = new Properties();
        props.put("mail.imap.host", imapHost);
        props.put("mail.imap.port", String.valueOf(imapPort));
        props.put("mail.imap.ssl.enable", String.valueOf(imapSsl));

        Session session = Session.getDefaultInstance(props);

        try (Store store = session.getStore("imap")) {
            store.connect(mailUsername, mailPassword);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Фильтр по отправителю (адресу банка)
            FromStringTerm senderFilter = new FromStringTerm(bankEmailSender);

            Message[] messages = inbox.search(senderFilter);

            logger.info("Найдено писем от {}: {}", bankEmailSender, messages.length);

            for (Message message : messages) {
                String uid = getMessageUid(inbox, message);
                if (emailLogRepository.findByUid(uid).isPresent()) {
                    continue;
                }

                String bodyText = EmailContentExtractor.getTextFromMessage(message);
                logger.info("Текст письма: {}", bodyText);

                // TODO: тут будет разбор bodyText для выделения суммы, типа операции и категории

                EmailLog log = EmailLog.builder()
                        .uid(uid)
                        .date(getSentDate(message))
                        .status("SPARSED")
                        .reason(null)
                        .build();

                emailLogRepository.save(log);
            }


            inbox.close(false);
            store.close();

        } catch (Exception e) {
            logger.error("Ошибка при синхронизации почты: ", e);
        }
    }

    private LocalDateTime getSentDate(Message message) throws MessagingException {
        return message.getSentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // Для получения UID письма в IMAP используем UIDFolder
    private String getMessageUid(Folder folder, Message message) throws MessagingException {
        if (folder instanceof UIDFolder) {
            UIDFolder uidFolder = (UIDFolder) folder;
            long uid = uidFolder.getUID(message);
            return String.valueOf(uid);
        }
        // Если UID получить нельзя - fallback по Message-ID
        String[] messageIds = message.getHeader("Message-ID");
        return (messageIds != null && messageIds.length > 0) ? messageIds[0] : null;
    }

    @Override
    public String getStatusByUid(String uid) {
        return emailLogRepository.findByUid(uid)
                .map(EmailLog::getStatus)
                .orElse("UNKNOWN");
    }

    @Override
    @Transactional
    public void clearLogs() {
        emailLogRepository.deleteAll();
        logger.info("Все логи email синхронизации удалены");
    }
}
