package com.orvyl.goose.config;

import com.orvyl.goose.service.UpskillSubscriptionService;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class SlackAppConfig {
    @Bean
    public AppConfig appConfig(@Value("${slack.team-bot-token}") String teamBotToken) {
        return AppConfig.builder()
                .singleTeamBotToken(teamBotToken)
                .build();
    }

    @Bean
    public App slackApplication(final AppConfig appConfig, final UpskillSubscriptionService upskillSubscriptionService) {
        App app = new App(appConfig);

        app.command("/upskills-status", (slashCommandRequest, slashCommandContext) -> {

            String param = slashCommandRequest.getPayload().getText();
            log.info("/upskills-status {}: {}", param, slashCommandRequest.getRequestBodyAsString());

            LocalDate now = LocalDate.now();
            LocalDate checkDate = param.isBlank()
                    ? now : LocalDate.of(now.getYear(), now.getMonth(), Integer.parseInt(param));

            List<LayoutBlock> responseBlocks = new ArrayList<>();

            HeaderBlock headerBlock = HeaderBlock.builder()
                    .text(PlainTextObject.builder().text("Upskills Summary Status").build())
                    .build();

            responseBlocks.add(headerBlock);
            responseBlocks.add(DividerBlock.builder().build());

            upskillSubscriptionService.getAllSummary(checkDate)
                    .forEach(summary -> {
                        SectionBlock sectionBlock = SectionBlock.builder()
                                .fields(
                                        Arrays.asList(
                                                MarkdownTextObject.builder().text("*" + summary.getCoachName() + "*").build(),
                                                MarkdownTextObject.builder().text("_Upskill Start Date:_ " + summary.getUpskillSubscriptionStart().format(DateTimeFormatter.ISO_DATE)).build(),
                                                MarkdownTextObject.builder().text(" ").build(),
                                                MarkdownTextObject.builder().text("_Upskill latest Payment:_ " + summary.getLastPaid().format(DateTimeFormatter.ISO_DATE)).build(),
                                                MarkdownTextObject.builder().text(" ").build(),
                                                MarkdownTextObject.builder().text("_Can join Masterclass:_ *" + (summary.isCanJoinMasterclass() ? "YES" : "NO") + "*").build()
                                        )
                                )
                                .build();
                        responseBlocks.add(sectionBlock);
                        responseBlocks.add(DividerBlock.builder().build());
                    });

            return slashCommandContext.ack(responseBlocks);
        });

        return app;
    }
}
