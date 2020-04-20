package rash.testProject.entity.technology;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HardCodeTechnologyItemFactory implements TechnologyItemFactory {
    private Map<String, TechnologyItem> map;

    public HardCodeTechnologyItemFactory() {
        TechnologyItem git = new TechnologyItem(1, "GIT");
        TechnologyItem springBoot = new TechnologyItem(2, "SPRING BOOT");
        TechnologyItem html = new TechnologyItem(3, "HTML");
        TechnologyItem javaEe = new TechnologyItem(4, "JAVA EE");
        TechnologyItem javaCore = new TechnologyItem(5, "JAVA CORE");
        TechnologyItem maven = new TechnologyItem(6, "MAVEN");
        TechnologyItem rest = new TechnologyItem(7, "REST");
        TechnologyItem spring = new TechnologyItem(8, "SPRING");

        map = Stream.of(git, springBoot, html, javaEe, javaCore, maven, rest, spring)
                .collect(
                        Collectors.toMap(TechnologyItem::getTitle, (item) -> item)
                );
    }

    @Override
    public TechnologyItem getTechnologyItem(String title) {
        return map.get(title);
    }
}
