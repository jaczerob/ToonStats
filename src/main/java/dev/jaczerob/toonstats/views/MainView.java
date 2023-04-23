package dev.jaczerob.toonstats.views;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.jaczerob.toonstats.entities.ToonEntity;
import dev.jaczerob.toonstats.services.toons.ToonService;

@Route("")
public class MainView extends VerticalLayout {
    // [{"id": 1, "name": "Bear", "image": "bear.png", "game": 1}, {"id": 2, "name": "Cat", "image": "cat.png", "game": 1}, {"id": 3, "name": "Dog", "image": "dog.png", "game": 1}, {"id": 4, "name": "Duck", "image": "duck.png", "game": 1}, {"id": 5, "name": "Horse", "image": "horse.png", "game": 1}, {"id": 6, "name": "Monkey", "image": "monkey.png", "game": 1}, {"id": 7, "name": "Mouse", "image": "mouse.png", "game": 1}, {"id": 8, "name": "Pig", "image": "pig.png", "game": 1}, {"id": 9, "name": "Rabbit", "image": "rabbit.png", "game": 1}, {"id": 10, "name": "Deer", "image": "deer.png", "game": 1}, {"id": 11, "name": "Crocodile", "image": "crocodile.png"
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
        final long totalToons = toonService.getToons().size();
        add(new Paragraph(String.format("Toon sample size obtained from scraping ToonHQ: %s", totalToons)));

        final Paragraph laffParagraph = new Paragraph();
        laffParagraph.getStyle().set("white-space", "pre-line");

        final long maxedToons = toonService.getToons().stream().filter(t -> t.getLaff() == 140).count();
        final String maxedPercentage = String.format("Toons with max laff: %.2f%%%n", (double) maxedToons / totalToons * 100);

        laffParagraph.add(maxedPercentage);

        final double averageLaff = toonService.getToons().stream().mapToLong(ToonEntity::getLaff).average().orElse(0);
        final String averageLaffPercentage = String.format("Average laff: %.2f%n", averageLaff);

        laffParagraph.add(averageLaffPercentage);

        add(laffParagraph);

        final Paragraph speciesParagraph = new Paragraph();
        speciesParagraph.getStyle().set("white-space", "pre-line");

        for (final Species species : Species.values()) {
            final long stat = toonService.getToons().stream().filter(t -> t.getSpecies() == species.ordinal() + 1).count();
            final String percentage = String.format("Toons with species %s: %.2f%%%n", species.name(), (double) stat / totalToons * 100);

            speciesParagraph.add(percentage);
        }

        add(speciesParagraph);

        final Paragraph organicsParagraph = new Paragraph();
        organicsParagraph.getStyle().set("white-space", "pre-line");

        for (final Organic organic : Organic.values()) {
            final long stat = toonService.getToons().stream().filter(t -> t.getOrganic() == organic.ordinal()).count();
            final String percentage = String.format("Toons with organic %s: %.2f%%%n", organic.name(), (double) stat / totalToons * 100);

            organicsParagraph.add(percentage);
        }

        add(organicsParagraph);

        final long toonsWithMaxSellbot = toonService.getToons().stream().filter(t -> t.getSellbot() == 8).count();
        final long toonsWithMaxCashbot = toonService.getToons().stream().filter(t -> t.getCashbot() == 8).count();
        final long toonsWithMaxLawbot = toonService.getToons().stream().filter(t -> t.getLawbot() == 8).count();
        final long toonsWithMaxBossbot = toonService.getToons().stream().filter(t -> t.getBossbot() == 8).count();

        final Paragraph cogTypesParagraph = new Paragraph();
        cogTypesParagraph.getStyle().set("white-space", "pre-line");

        cogTypesParagraph.add(String.format("Toons with max Sellbot: %.2f%%%n", (double) toonsWithMaxSellbot / totalToons * 100));
        cogTypesParagraph.add(String.format("Toons with max Cashbot: %.2f%%%n", (double) toonsWithMaxCashbot / totalToons * 100));
        cogTypesParagraph.add(String.format("Toons with max Lawbot: %.2f%%%n", (double) toonsWithMaxLawbot / totalToons * 100));
        cogTypesParagraph.add(String.format("Toons with max Bossbot: %.2f%%%n", (double) toonsWithMaxBossbot / totalToons * 100));

        add(cogTypesParagraph);
    }
}