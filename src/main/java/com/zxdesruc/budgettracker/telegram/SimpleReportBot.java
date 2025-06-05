package com.zxdesruc.budgettracker.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimpleReportBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;

    // ���� � �������� �� ���� ���������� (����� ����)
    private final String reportImagePath = "C:\\Users\\O.Razgonov\\IdeaProjects\\budget-tracker\\image.png";

    public SimpleReportBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Message message = update.getMessage();
        String text = message.getText();

        if (text.equals("/start") || text.equalsIgnoreCase("�����")) {
            sendMessageWithKeyboard(message.getChatId().toString(), "������! �������� ��������:");
        } else if (text.equalsIgnoreCase("�������� �����")) {
            sendReportImage(message.getChatId().toString());
        } else {
            sendMessageWithKeyboard(message.getChatId().toString(), "����������� �������. ����������� ������.");
        }
    }

    private void sendMessageWithKeyboard(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        // ���������� � ����� ��������
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("�����"));
        row.add(new KeyboardButton("�������� �����"));
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendReportImage(String chatId) {
        File file = new File(reportImagePath);
        if (!file.exists() || !file.isFile()) {
            sendMessage(chatId, "����� ���� ����������: ���� �� ������.");
            return;
        }

        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId);

        InputFile inputFile = new InputFile(file); // <- ����� ������ �� File, � �� �� String
        photo.setPhoto(inputFile);
        photo.setCaption("��� ��� �����");

        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendMessage(chatId, "������ ��� �������� ��������.");
        }
    }


    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("�������������: java SimpleReportBot <botUsername> <botToken>");
            System.exit(1);
        }
        String botUsername = args[0];
        String botToken = args[1];

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new SimpleReportBot(botUsername, botToken));

        System.out.println("��� �������...");
    }
}
