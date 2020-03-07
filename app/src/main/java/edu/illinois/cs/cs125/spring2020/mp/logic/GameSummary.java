package edu.illinois.cs.cs125.spring2020.mp.logic;

import android.content.Context;

public class GameSummary {
    /**variable in com.google.gson.JsonObject.*/
    private com.google.gson.JsonObject hi;
    /**Creates a game summary from JSON from the server.
     * @param infoFromServer - one object from the array in the /games response.
     * */
    public GameSummary(final com.google.gson.JsonObject infoFromServer) {
        hi = infoFromServer;
    }
    /**Gets the unique, server-assigned ID of this game.
     * @return the game ID.*/
    public String getId() {
        return null;
    }
    /**Gets the mode of this game, either area or target.
     * @return the game mode*/
    public String getMode() {
        return null;
    }
    /**Gets the owner/creator of this game.
     * @return the email of the game's owner*/
    public String getOwner() {
        return null;
    }
    /**Gets the name of the user's team/role.
     * @param userEmail - the logged-in user's email
     * @param context - an Android context (for access to resources)
     * @return the human-readable team/role name of the user in this game.*/
    public String getPlayerRole(final String userEmail, final Context x) {
        return null;
    }
    /**Determines whether this game is an invitation to the user.
     * @param userEmail - the logged-in user's email
     * @return whether the user is invited to this game.*/
    public boolean isOngoing(final String userEmail) {
        return true;
    }
    /**Determines whether the user is currently involved in this game
     * @param userEmail - the logged-in user's email
     * @return whether this game is ongoing for the user*/
    public boolean isInvitation(String userEmail) {
        return true;
    }
}
