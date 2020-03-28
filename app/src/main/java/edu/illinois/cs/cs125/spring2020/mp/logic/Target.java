package edu.illinois.cs.cs125.spring2020.mp.logic;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Target {
    /**blue team.*/
    private static final float HUE_BLUE = (float) 240.0;
    /**green team.*/
    private static final float HUE_GREEN = (float) 120.0;
    /**red team.*/
    private static final float HUE_RED = (float) 0.0;
    /**unclaimed team.*/
    private static final float HUE_VIOLET = (float) 270.0;
    /**yellow team.*/
    private static final float HUE_YELLOW = (float) 60.0;
    /**google map.*/
    private GoogleMap map;
    /**latlng position.*/
    private LatLng position;
    /**team.*/
    private int team;
    /**marker.*/
    private Marker marker;
    /**Creates a target in a target-mode game by placing an appropriately colored marker on the map.
     * The marker's hue should reflect the team (if any) currently owning the target. See the class
     * description for the hue values to use.
     * @param setMap - the map to render to
     * @param setPosition - the position of the target
     * @param setTeam - the TeamID code of the team currently owning the target */
    public Target(final GoogleMap setMap, final LatLng setPosition, final int setTeam) {
        map = setMap;
        position = setPosition;
        MarkerOptions options = new MarkerOptions().position(position);
        marker = map.addMarker(options);
        setTeam(setTeam);
    }
    /**Gets the position of the target.
     * @return the coordinates of the target
     * */
    public LatLng getPosition() {
        return position;
    }
    /** Gets the ID of the team currently owning this target.
     * @return the owning team ID or OBSERVER if unclaimed
     */
    public int getTeam() {
        return team;
    }
    /**Updates the owning team of this target and updates the hue of the marker to match.
     * @param newTeam - the ID of the team that captured the target
     * */
    public void setTeam(final int newTeam) {
        team = newTeam;
        if (team == TeamID.TEAM_RED) {
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            marker.setIcon(icon);
        } else if (team == TeamID.TEAM_YELLOW) {
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            marker.setIcon(icon);
        } else if (team == TeamID.TEAM_GREEN) {
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            marker.setIcon(icon);
        } else if (team == TeamID.TEAM_BLUE) {
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            marker.setIcon(icon);
        } else {
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
            marker.setIcon(icon);
        }
    }
}

