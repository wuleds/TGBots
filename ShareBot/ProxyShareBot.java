package cn.wule;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.InputStream;
import java.util.List;

public class ProxyShareBot extends TelegramLongPollingBot
{
    private static final String BOT_TOKEN = "6863572786:AAHqt9hTmbkiidgiJ88_guc4oxck7Hlx2_0";
    private static final String BOT_USERNAME = "hjnu_share_bot";
    private static final String CHAT_ID = "-1001844968002";

    @Override
    public void onUpdateReceived(Update update)
    {
        DeleteWebhook deleteWebhook = new DeleteWebhook();
        try {
            execute(deleteWebhook);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                SendMessage message = new SendMessage();
                message.setChatId(CHAT_ID);
                message.setText(messageText);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (update.getMessage().hasAudio()) {
                String fileId = update.getMessage().getAudio().getFileId();
                SendAudio sendAudio = new SendAudio();
                sendAudio.setChatId(CHAT_ID);
                sendAudio.setAudio(getInputFile(fileId));
                try {
                    execute(sendAudio);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (update.getMessage().hasPhoto()) {
                List<PhotoSize> fileIds = update.getMessage().getPhoto();
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(CHAT_ID);

                sendPhoto.setPhoto(getInputFile(fileIds.get(2).getFileId()));
                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (update.getMessage().hasVideo()) {
                String fileId = update.getMessage().getVideo().getFileId();
                SendAudio sendAudio = new SendAudio();
                sendAudio.setChatId(CHAT_ID);
                sendAudio.setAudio(getInputFile(fileId));
                try {
                    execute(sendAudio);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (update.getMessage().hasDocument()) {
                String fileId = update.getMessage().getDocument().getFileId();
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(CHAT_ID);
                sendDocument.setDocument(getInputFile(fileId));
                try {
                    execute(sendDocument);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private InputFile getInputFile(String fileId) {
        GetFile getFileMethod = new GetFile();
        InputFile inputFile = null;
        getFileMethod.setFileId(fileId);
        try {
            File file = execute(getFileMethod);
            InputStream fileStream = downloadFileAsStream(file);
            inputFile = new InputFile(fileStream, file.getFilePath());
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
        return inputFile;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            System.out.println("Bot started");
            botsApi.registerBot(new ProxyShareBot());
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }
    }
}