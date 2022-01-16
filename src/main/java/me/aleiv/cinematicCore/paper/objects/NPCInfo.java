package me.aleiv.cinematicCore.paper.objects;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.profile.Profile;
import lombok.Getter;
import lombok.Setter;
import me.aleiv.cinematicCore.paper.files.BasicLocation;
import me.aleiv.cinematicCore.paper.utilities.LocationUtils;
import me.aleiv.cinematicCore.paper.utilities.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class NPCInfo {

    @Getter private final UUID uuid;

    private Profile profile;
    private BasicLocation location;
    @Getter private boolean overlay;
    @Getter private boolean lookAtPlayer;
    @Getter private boolean hideNameTag;
    @Getter private String teamName;

    private NPCItems npcItems;
    @Getter @Setter private boolean cache = false;

    public NPCInfo(Player player) {
        this(player, false, true, true);
    }

    public NPCInfo(Player player, boolean lookAtPlayer) {
        this(player, lookAtPlayer, true, true);
    }

    public NPCInfo(Player player, boolean lookAtPlayer, boolean hideNameTag) {
        this(player, true, lookAtPlayer, hideNameTag);
    }

    public NPCInfo(Player player, boolean lookAtPlayer, boolean overlay, boolean hideNameTag) {
        this.uuid = UUID.randomUUID();
        this.profile = this.createProfile(player);
        this.location = new BasicLocation(LocationUtils.getSafeLocation(player.getLocation().clone()));
        this.overlay = overlay;
        this.lookAtPlayer = lookAtPlayer;
        this.hideNameTag = hideNameTag;

        this.npcItems = new NPCItems(player);
        this.createTeamName();
    }

    public NPCInfo(Profile profile, BasicLocation location, boolean lookAtPlayer, boolean overlay, boolean hideNameTag) {
        this(profile, location, new NPCItems(), lookAtPlayer, overlay, hideNameTag);
    }

    public NPCInfo(Profile profile, BasicLocation location, NPCItems items, boolean lookAtPlayer, boolean overlay, boolean hideNameTag) {
        this(UUID.randomUUID(), profile, location, items, lookAtPlayer, overlay, hideNameTag);
    }

    public NPCInfo(UUID uuid, Profile profile, BasicLocation location, NPCItems items, boolean lookAtPlayer, boolean overlay, boolean hideNameTag) {
        this.uuid = uuid;
        this.profile = profile;
        this.location = location;
        this.overlay = overlay;
        this.lookAtPlayer = lookAtPlayer;
        this.hideNameTag = hideNameTag;

        this.npcItems = items;
        this.createTeamName();
    }

    private Profile createProfile(Player player) {
        Profile profile = new Profile(player.getUniqueId());
        profile.complete();

        profile.setName(player.getName());
        profile.setUniqueId(UUID.randomUUID());

        return profile;
    }

    public NPC.Builder createBuilder() {
        if (this.hideNameTag)
            ScoreboardUtils.createNametagTeam(this.profile.getName(), this.teamName);

        return NPC.builder()
                .location(this.location.getLocation())
                .profile(this.profile)
                .lookAtPlayer(this.lookAtPlayer)
                .imitatePlayer(false);
    }

    public Profile getProfile() {
        return profile.clone();
    }

    public BasicLocation getLocation() {
        return location.clone();
    }

    public NPCItems getNPCItems() {
        return npcItems.clone();
    }

    private void createTeamName() {
        this.teamName = "sc_npc_" + UUID.randomUUID().toString().substring(0, 8);
    }

}
