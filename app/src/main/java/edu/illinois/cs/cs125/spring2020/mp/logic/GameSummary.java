package edu.illinois.cs.cs125.spring2020.mp.logic;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.illinois.cs.cs125.spring2020.mp.R;

public class GameSummary {
    /**JsonObject info.*/
    private JsonObject info;
    /**players.*/
    private JsonArray players;
    /**Creates a game summary from JSON from the server.
     * @param infoFromServer - one object from the array in the /games response.
     * */
    public GameSummary(final com.google.gson.JsonObject infoFromServer) {
        info = infoFromServer;
        players = info.get("players").getAsJsonArray();
    }
    /**Gets the unique, server-assigned ID of this game.
     * @return the game ID.*/
    public String getId() {
        if (info.get("id").getAsString() != null) {
            return info.get("id").getAsString();
        }
        return null;
    }
    /**Gets the mode of this game, either area or target.
     * @return the game mode*/
    public String getMode() {
        if (info.get("mode").getAsString() != null) {
            return info.get("mode").getAsString();
        }
        return null;
    }
    /**Gets the owner/creator of this game.
     * @return the email of the game's owner*/
    public String getOwner() {
        if (info.get("owner").getAsString() != null) {
            return info.get("owner").getAsString();
        }
        return null;
    }
    /**Gets the name of the user's team/role.
     * @param userEmail - the logged-in user's email
     * @param context - an Android context (for access to resources)
     * @return the human-readable team/role name of the user in this game.*/
    public String getPlayerRole(final String userEmail, final Context context) {
        for (JsonElement p : players) {
            JsonObject player = (JsonObject) p;
            if (player.get("email").getAsString().equals(userEmail)) {
                String[] teamNames = context.getResources().getStringArray(R.array.team_choices);
                for (int i = 0; i < teamNames.length; i++) {
                    return teamNames[player.get("team").getAsInt()];
                }
            }
        }
        return null;
    }
    /**Determines whether this game is an invitation to the user.
     * @param userEmail - the logged-in user's email
     * @return whether the user is invited to this game.*/
    public boolean isOngoing(final String userEmail) {
        for (JsonElement p : players) {
            JsonObject player = (JsonObject) p;
            if (player.get("email").getAsString() == userEmail) {
                if (player.get("state").getAsInt() != GameStateID.ENDED) {
                    return true;
                }
            }
        }
        return false;
    }
    /**Determines whether the user is currently involved in this game
     * @param userEmail - the logged-in user's email
     * @return whether this game is ongoing for the user*/
    public boolean isInvitation(final String userEmail) {
        if (info.get("state").getAsInt() != GameStateID.ENDED) {
            for (JsonElement p : players) {
                JsonObject player = (JsonObject) p;
                if (player.get("email").getAsString() == userEmail) {
                    if (player.get("state").getAsInt() == PlayerStateID.INVITED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
