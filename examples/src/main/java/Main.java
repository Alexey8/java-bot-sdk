import java.util.concurrent.ExecutionException;

import im.dlg.botsdk.Bot;
import im.dlg.botsdk.domain.content.DocumentContent;
import im.dlg.botsdk.domain.content.TextContent;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Bot bot = Bot.start("cbb4994cabfa8d2a5bce0b5f7a44c23da943f767").get();

        bot.messaging().onMessage(message ->
                bot.users().get(message.getSender()).thenAccept(userOpt -> userOpt.ifPresent(user -> {
                            System.out.println("Got a message: " + message.getText() + " from user: " + user.getName());
                        })
                ).thenCompose(aVoid -> {
                            if (message.getText().equals("Send me photo")) {
                                bot.messaging().sendMedia(message.getPeer(), ((TextContent) message.getMessageContent()).getMedias());
                            } else if (message.getText().equals("Send me video")) {
                                bot.messaging().sendDocument(message.getPeer(), (DocumentContent) message.getMessageContent());
                            } else {
//                                bot.interactiveApi().send(message.getPeer())
//                                bot.interactiveApi().send(message.getPeer(), new InteractiveGroup())
                            }
                            return null;
                        }
                ).thenAccept(uuid ->
                        System.out.println("Sent a message with UUID: " + uuid)));

        bot.await();
    }
}