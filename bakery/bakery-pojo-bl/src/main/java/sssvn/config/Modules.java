package sssvn.config;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sssvn.security.tokens.LogisticsModuleToken;
import sssvn.security.tokens.OtherModuleToken;
import sssvn.security.tokens.ProductionModuleToken;
import sssvn.security.tokens.UsersAndPersonnelModuleToken;
import ua.com.fielden.platform.security.ISecurityToken;
import ua.com.fielden.platform.utils.StreamUtils;

/**
 * Enumerates application modules and their configurations.
 *
 * @author Generated
 */

public enum Modules {
    USERS_AND_PERSONNEL("Users and Personnel", "A module to manage systems users and company personnel.",
            "mainMenu:help", "#ffeb3b", "#c8b900",
            UsersAndPersonnelModuleToken.class,
            "sssvn.personnel",
            "ua.com.fielden.platform.security.user"),
    LOGISTICS("Logistics", "A module to manage company logistics.",
            "mainMenu:store", "#1976d2", "#004ba0",
            LogisticsModuleToken.class,
            "sssvn.logistics"),
    PRODUCTION("Production", "A module to manage company's production.",
            "mainMenu:equipment", "#ff9800", "#c66900",
            ProductionModuleToken.class,
            "sssvn.production"),
    OTHER("Other", "Entities that have not been or cannot be classified as belonging to one specific module.",
            "", "", "",
           OtherModuleToken.class,
           "ua.com.fielden.platform.attachment");

    public final Set<String> packagesAndEntityNames; // this set is immutable
    public final String title;
    public final String desc;
    public final String icon;
    public final String bgColour;
    public final String captionBgColour;
    public final Class<? extends ISecurityToken> topSecurityToken;

    Modules(final String title, final String desc,
            final String icon, final String bgColour, final String captionBgColour,
            final Class<? extends ISecurityToken> topSecurityToken, final String packageName, final String... morePackageNames) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.bgColour = bgColour;
        this.captionBgColour = captionBgColour;
        this.topSecurityToken = topSecurityToken;
        packagesAndEntityNames = Collections.unmodifiableSet(StreamUtils.of(packageName, morePackageNames).collect(Collectors.toSet()));
    }

    /**
     * Identifies the the best-matching module for the specified package or full class name as defined by a set of packages for each module.
     * <p>
     * The best-matching module is the module with the longest matching package/class name.
     *
     * @param packageOrClassName
     * @return an empty optional is if none of the modules contain the specified package or its super-package.
     */
    public static Modules moduleFor(final String packageOrClassName) {
        final Comparator<String> cmpByLength = (s1, s2) -> s2.length() - s1.length();
        return Stream.of(Modules.values())
                // quick scan for modules containing at least one match from the start
                .filter(module -> module.packagesAndEntityNames.stream().anyMatch(packageOrClassName::startsWith))
                // identify the best-matching module by comparing the lengths of the longest matches within modules
                .sorted((m1, m2) -> {
                    final int longestMatchInModule1 = m1.packagesAndEntityNames.stream().filter(packageOrClassName::startsWith).sorted(cmpByLength).findFirst().map(String::length).orElse(0);
                    final int longestMatchInModule2 = m2.packagesAndEntityNames.stream().filter(packageOrClassName::startsWith).sorted(cmpByLength).findFirst().map(String::length).orElse(0);
                    return longestMatchInModule2 - longestMatchInModule1;
                })
                .findFirst().orElse(OTHER);
    }
}
