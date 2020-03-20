package me.Arii.ServerStatusPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//Player types command
		if(label.equalsIgnoreCase("websiteupdate")) {

			//HTTP Connection to a website + sending server data using HTTP POST Method
			try {
				//Connection Parameters
				//-> URL in the form of http://...
		        URL url = new URL("");
		        URLConnection urlConnection = url.openConnection();
		        //Set Request Properties
		        urlConnection.setRequestProperty("accept", "*/*");
		        urlConnection.setRequestProperty("connection", "Keep-Alive");
		        urlConnection.setRequestProperty("X-Requested-With","XMLHttpRequest");
		        //Time constraint for errors (timeout)
		        urlConnection.setConnectTimeout(5000);
		        urlConnection.setReadTimeout(1000);
		        urlConnection.setDoOutput(true);
		        //Establish Connection
		        urlConnection.connect();
		        
		        /*Data which you want to send,multipe parameters possible, example:
		        "id=4&passwd=123123&mode=0";
		        */
		        String param = "playersOnline="+Bukkit.getOnlinePlayers().size();
		        
		        //Send output message to server -> Testing Purpose
		        sender.sendMessage("Sent following data to server: "+param);
		        
		        PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());
		        pw.print(param); 
		        pw.flush(); 
		        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		        String line;
		        while((line=in.readLine())!=null){
		            new String(line.getBytes(),"gbk");
		        }
		        
				}
				catch (IOException ex) {
				   sender.sendMessage("Something went wrong -> IOException");
				   ex.printStackTrace();
				   //Unsuccessful Data Post
				   return false;
				}
			
			//Successful Data Post
			return true;
		}
		//Player didn't type command
		return false;
	}
}
