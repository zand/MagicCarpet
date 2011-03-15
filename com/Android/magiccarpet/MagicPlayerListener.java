package com.Android.magiccarpet;

import java.util.Hashtable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
* Magic Carpet 1.4
* Copyright (C) 2011 Android <spparr@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

/**
* MagicPlayerListener.java
* <br /><br />
* Listens for calls for the magic carpet, makes a carpet when a player logs on, removes one when a player logs off,
* and moves the carpet when the player moves.
*
* @author Android <spparr@gmail.com>
*/
public class MagicPlayerListener extends PlayerListener {
	private Hashtable<String, Carpet> carpets = new Hashtable<String, Carpet>();
		
	@Override
    //When a player joins the game, if they had a carpet when the logged out it puts it back.
    public void onPlayerJoin(PlayerEvent event) {
    	Player player = event.getPlayer();
    	Carpet carpet = (Carpet)carpets.get(player.getName());
    	if (carpet == null)
    		return;
    	carpet.drawCarpet();    
	}

    @Override
    //When a player quits, it removes the carpet from the server
    public void onPlayerQuit(PlayerEvent event) {
    	Player player = event.getPlayer();
    	Carpet carpet = (Carpet)carpets.get(player.getName());
		if (carpet == null)
			return;
		carpet.removeCarpet();
    }

    @Override
    //Lets the carpet move with the player
    public void onPlayerMove(PlayerMoveEvent event) {
    	Location from = event.getFrom().clone();
    	Location to = event.getTo().clone();
    	Player player = event.getPlayer();
    	Carpet carpet = (Carpet)carpets.get(player.getName());
    	if (carpet == null)
    		return;
    	carpet.removeCarpet();
    	to.setY(to.getY()-1);
    	if(from.getPitch() == 90 && (to.getX() != from.getX() || to.getZ() != from.getZ()))
    		to.setY(to.getY()-1);
    	carpet.currentBlock = to.getBlock();
    	carpet.drawCarpet();
    }
    
    public void onPlayerTeleport (PlayerMoveEvent event) {
    	Location to = event.getTo().clone();
    	Player player = event.getPlayer();
    	// Check if the player has a carpet
        Carpet carpet = (Carpet)carpets.get(player.getName());
        if (carpet == null)
        	return;
       
        // Sets the location just under the player
        to.setY(to.getY()-1);
        
        // Check if the player moved 1 block
        // TODO the next line is throwing a null pointer exception
        Location last = carpet.currentBlock.getLocation();
        if (last.getBlockX() == to.getBlockX() &&
        	last.getBlockY() == to.getBlockY() &&
        	last.getBlockZ() == to.getBlockZ())
        		return;
       
        // Move the carpet
        carpet.removeCarpet();
    	carpet.currentBlock = to.getBlock();
    	carpet.drawCarpet();
    }
    
    public Hashtable<String, Carpet> getCarpets(){
    	return carpets;
    }
    
    public void setCarpets(Hashtable<String, Carpet> carp){
    	carpets = carp;
    }
}