package dev.jaczerob.toonstats.views;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.legend.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.subtitle.builder.StyleBuilder;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.services.toons.ToonService;

import java.util.List;
import java.util.stream.Stream;

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
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(new H1("Toon Stats"));

        final List<ToonDTO> toons = toonService.getToons();
        final List<ToonDTO> toonsAbove100Laff = toons.stream().filter(t -> t.getLaff() >= 100).toList();

        add(new H4("Total Toons as scraped from ToonHQ's ToonSync: " + toons.size()));

        add(createSpeciesChart(toons));
        add(createOrganicChart(toons));
        add(createToonsAbove100LaffWithTier8BossbotSuit(toonsAbove100Laff));
        add(createToonsAbove100LaffWithTier8LawbotSuit(toonsAbove100Laff));
        add(createToonsAbove100LaffWithTier8CashbotSuit(toonsAbove100Laff));
        add(createToonsAbove100LaffWithTier8SellbotSuit(toonsAbove100Laff));
    }

    private static ApexCharts createPieChart(final String title, final List<String> labels, final List<Double> series) {
        final ApexCharts apexCharts = ApexChartsBuilder.get()
                .withTitle(TitleSubtitleBuilder.get().withText(title).withStyle(StyleBuilder.get().withColor("white").build()).build())
                .withChart(ChartBuilder.get().withType(Type.PIE).build())
                .withLabels(labels.toArray(new String[0]))
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.LEFT)
                        .withLabels(LabelsBuilder.get()
                                .withUseSeriesColors(true)
                                .build())
                        .build())
                .withSeries(series.toArray(new Double[0]))
                .build();

        apexCharts.setHeight("480px");
        apexCharts.setWidth("480px");

        return apexCharts;
    }

    private static ApexCharts createSpeciesChart(final List<ToonDTO> toons) {
        final List<String> labels = Stream.of(Species.values()).map(Enum::name).toList();
        final List<Double> series = Stream.of(Species.values()).map(s -> Double.valueOf(toons.stream().filter(t -> t.getSpecies() == s.ordinal() + 1).count())).toList();
        return createPieChart("Species", labels, series);
    }

    private static ApexCharts createOrganicChart(final List<ToonDTO> toons) {
        final List<String> labels = Stream.of(Organic.values()).map(Enum::name).toList();
        final List<Double> series = Stream.of(Organic.values()).map(o -> Double.valueOf(toons.stream().filter(t -> t.getOrganic() == o.ordinal()).count())).toList();
        return createPieChart("Organic", labels, series);
    }

    private static ApexCharts createToonsAbove100LaffWithTier8BossbotSuit(final List<ToonDTO> toons) {
        final List<String> labels = List.of("With", "Without");

        final double toonsWithTier8BossbotSuit = (double) toons.stream().filter(t -> t.getBossbot() == 8).count();
        final Double toonsWithoutTier8BossbotSuit = toons.size() - toonsWithTier8BossbotSuit;

        final List<Double> series = List.of(toonsWithTier8BossbotSuit, toonsWithoutTier8BossbotSuit);

        return createPieChart("Toons Above 100 Laff With Tier 8 Bossbot Suit", labels, series);
    }

    private static ApexCharts createToonsAbove100LaffWithTier8LawbotSuit(final List<ToonDTO> toons) {
        final List<String> labels = List.of("With", "Without");

        final double toonsWithTier8LawbotSuit = (double) toons.stream().filter(t -> t.getLawbot() == 8).count();
        final Double toonsWithoutTier8LawbotSuit = toons.size() - toonsWithTier8LawbotSuit;

        final List<Double> series = List.of(toonsWithTier8LawbotSuit, toonsWithoutTier8LawbotSuit);

        return createPieChart("Toons Above 100 Laff With Tier 8 Lawbot Suit", labels, series);
    }

    private static ApexCharts createToonsAbove100LaffWithTier8CashbotSuit(final List<ToonDTO> toons) {
        final List<String> labels = List.of("With", "Without");

        final double toonsWithTier8CashbotSuit = (double) toons.stream().filter(t -> t.getCashbot() == 8).count();
        final Double toonsWithoutTier8CashbotSuit = toons.size() - toonsWithTier8CashbotSuit;

        final List<Double> series = List.of(toonsWithTier8CashbotSuit, toonsWithoutTier8CashbotSuit);

        return createPieChart("Toons Above 100 Laff With Tier 8 Cashbot Suit", labels, series);
    }

    private static ApexCharts createToonsAbove100LaffWithTier8SellbotSuit(final List<ToonDTO> toons) {
        final List<String> labels = List.of("With", "Without");

        final double toonsWithTier8SellbotSuit = (double) toons.stream().filter(t -> t.getSellbot() == 8).count();
        final Double toonsWithoutTier8SellbotSuit = toons.size() - toonsWithTier8SellbotSuit;

        final List<Double> series = List.of(toonsWithTier8SellbotSuit, toonsWithoutTier8SellbotSuit);

        return createPieChart("Toons Above 100 Laff With Tier8 Sellbot Suit", labels, series);
    }
}
