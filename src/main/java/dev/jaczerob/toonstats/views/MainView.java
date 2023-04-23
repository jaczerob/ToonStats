package dev.jaczerob.toonstats.views;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.services.toons.ToonService;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {
    public enum Species {
        Bear,
        Cat,
        Dog,
        Duck,
        Horse,
        Monkey,
        Mouse,
        Pig,
        Rabbit,
        Deer,
        Crocodile
    }

    public enum Organic {
        None,
        ToonUp,
        Trap,
        Lure,
        Sound,
        Throw,
        Squirt,
        Drop
    }

    public MainView(final ToonService toonService) {
        final List<ToonDTO> toons = toonService.getToons();

        add(getLaffParagraph(toons));
        add(getSpeciesParagraph(toons));
        add(getOrganicsParagraph(toons));
        add(getOrganicsAbove100LaffParagraph(toons));
        add(getCogTypesParagraph(toons));
        add(getCogTypesAbove100LaffParagraph(toons));
    }

    private static Paragraph initParagraph() {
        final Paragraph paragraph = new Paragraph();
        paragraph.getStyle().set("white-space", "pre-line");
        return paragraph;
    }

    private static Paragraph getCogTypesAbove100LaffParagraph(final List<ToonDTO> toons) {
        final Paragraph paragraph = initParagraph();

        final long totalToonsAbove100Laff = toons.stream().filter(t -> t.getLaff() >= 100).count();

        final long toonsWithMaxSellbot = toons.stream().filter(t -> t.getLaff() >= 100 && t.getSellbot() == 8).count();
        final long toonsWithMaxCashbot = toons.stream().filter(t -> t.getLaff() >= 100 && t.getCashbot() == 8).count();
        final long toonsWithMaxLawbot = toons.stream().filter(t -> t.getLaff() >= 100 && t.getLawbot() == 8).count();
        final long toonsWithMaxBossbot = toons.stream().filter(t -> t.getLaff() >= 100 && t.getBossbot() == 8).count();

        paragraph.add(String.format("Toons above 100 laff with the Mr. Hollywood suit: %.2f%%%n", (double) toonsWithMaxSellbot / totalToonsAbove100Laff * 100));
        paragraph.add(String.format("Toons above 100 laff with the Robber Baron suit: %.2f%%%n", (double) toonsWithMaxCashbot / totalToonsAbove100Laff * 100));
        paragraph.add(String.format("Toons above 100 laff with the Big Wig suit: %.2f%%%n", (double) toonsWithMaxLawbot / totalToonsAbove100Laff * 100));
        paragraph.add(String.format("Toons above 100 laff with the Big Cheese suit: %.2f%%%n", (double) toonsWithMaxBossbot / totalToonsAbove100Laff * 100));

        return paragraph;
    }

    private static Paragraph getOrganicsAbove100LaffParagraph(final List<ToonDTO> toons) {
        final Paragraph paragraph = initParagraph();

        final long totalToonsAbove100Laff = toons.stream().filter(t -> t.getLaff() >= 100).count();

        for (final Organic organic : Organic.values()) {
            final long stat = toons.stream().filter(t -> t.getLaff() >= 100 && t.getOrganic() == organic.ordinal()).count();
            final String percentage = String.format("Toons above 100 laff with organic %s: %.2f%%%n", organic.name(), (double) stat / totalToonsAbove100Laff * 100);

            paragraph.add(percentage);
        }

        return paragraph;
    }

    private static Paragraph getLaffParagraph(final List<ToonDTO> toons) {
        final Paragraph paragraph = initParagraph();

        final long maxedToons = toons.stream().filter(t -> t.getLaff() == 140).count();
        final String maxedPercentage = String.format("Toons with max laff: %.2f%%%n", (double) maxedToons / toons.size() * 100);

        paragraph.add(maxedPercentage);

        final double averageLaff = toons.stream().mapToLong(ToonDTO::getLaff).average().orElse(0);
        final String averageLaffPercentage = String.format("Average laff: %.2f%n", averageLaff);

        paragraph.add(averageLaffPercentage);

        return paragraph;
    }

    private static Paragraph getSpeciesParagraph(final List<ToonDTO> toons) {
        final Paragraph paragraph = initParagraph();

        for (final Species species : Species.values()) {
            final long stat = toons.stream().filter(t -> t.getSpecies() == species.ordinal() + 1).count();
            final String percentage = String.format("Toons with species %s: %.2f%%%n", species.name(), (double) stat / toons.size() * 100);

            paragraph.add(percentage);
        }

        return paragraph;
    }

    private static Paragraph getOrganicsParagraph(final List<ToonDTO> toons) {
        final Paragraph paragraph = initParagraph();

        for (final Organic organic : Organic.values()) {
            final long stat = toons.stream().filter(t -> t.getOrganic() == organic.ordinal()).count();
            final String percentage = String.format("Toons with organic %s: %.2f%%%n", organic.name(), (double) stat / toons.size() * 100);

            paragraph.add(percentage);
        }

        return paragraph;
    }

    private static Paragraph getCogTypesParagraph(final List<ToonDTO> toons) {
        final Paragraph paragraph = initParagraph();

        final long toonsWithMaxSellbot = toons.stream().filter(t -> t.getSellbot() == 8).count();
        final long toonsWithMaxCashbot = toons.stream().filter(t -> t.getCashbot() == 8).count();
        final long toonsWithMaxLawbot = toons.stream().filter(t -> t.getLawbot() == 8).count();
        final long toonsWithMaxBossbot = toons.stream().filter(t -> t.getBossbot() == 8).count();

        paragraph.add(String.format("Toons with the Mr. Hollywood suit: %.2f%%%n", (double) toonsWithMaxSellbot / toons.size() * 100));
        paragraph.add(String.format("Toons with the Robber Baron suit: %.2f%%%n", (double) toonsWithMaxCashbot / toons.size() * 100));
        paragraph.add(String.format("Toons with the Big Wig suit: %.2f%%%n", (double) toonsWithMaxLawbot / toons.size() * 100));
        paragraph.add(String.format("Toons with the Big Cheese suit: %.2f%%%n", (double) toonsWithMaxBossbot / toons.size() * 100));

        return paragraph;
    }
}
