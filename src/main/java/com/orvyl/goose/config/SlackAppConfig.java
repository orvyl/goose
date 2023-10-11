package com.orvyl.goose.config;

import com.orvyl.goose.model.UpskillStatusSummary;
import com.orvyl.goose.service.UpskillSubscriptionService;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

            List<TextObject> collect = upskillSubscriptionService.getAllSummary(LocalDate.now()).stream()
                    .map(new Function<UpskillStatusSummary, List<TextObject>>() {
                        @Override
                        public List<TextObject> apply(UpskillStatusSummary summary) {
                            return Arrays.asList(
                                    MarkdownTextObject.builder().text("*" + summary.getCoachName() + "*").build(),
                                    MarkdownTextObject.builder().text("_Upskill Start Date:_ " + summary.getUpskillSubscriptionStart().format(DateTimeFormatter.ISO_DATE)).build(),
                                    MarkdownTextObject.builder().text(" ").build(),
                                    MarkdownTextObject.builder().text("_Upskill latest Payment:_ " + summary.getLastPaid().format(DateTimeFormatter.ISO_DATE)).build(),
                                    MarkdownTextObject.builder().text(" ").build(),
                                    MarkdownTextObject.builder().text("_Can join Masterclass:_ *" + (summary.isCanJoinMasterclass() ? "YES" : "NO") + "*").build()
                            );
                        }
                    })
                    .flatMap(Collection::stream)
                    .toList();

            HeaderBlock headerBlock = HeaderBlock.builder()
                    .text(PlainTextObject.builder().text("Upskills Summary Status").build())
                    .build();
            DividerBlock dividerBlock = DividerBlock.builder().build();
            SectionBlock sectionBlock = SectionBlock.builder()
                    .fields(
                            Arrays.asList(
                                    MarkdownTextObject.builder().text("*Coach Orvyl*").build(),
                                    MarkdownTextObject.builder().text("_Upskill Start Date:_ July 4, 2020").build(),
                                    MarkdownTextObject.builder().text(" ").build(),
                                    MarkdownTextObject.builder().text("_Upskill latest Payment:_ Oct 4, 2023").build(),
                                    MarkdownTextObject.builder().text(" ").build(),
                                    MarkdownTextObject.builder().text("_Can join Masterclass:_ *YES*").build()
                            )
                    )
                    .build();

            return slashCommandContext.ack(Arrays.asList(headerBlock, dividerBlock, sectionBlock));
        });

        return app;
    }
}
