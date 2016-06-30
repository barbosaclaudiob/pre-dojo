package br.com.killerslog.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Partida {

    private String codpart;
    private Map<String, Player> mapPlayer = new HashMap();
    private String inicioDaPartida;
    private String fimDaPartida;

    public Partida() {
    }

    public void iniciaPartida(String log) {
        if (codpart == null) {
            if (log.contains("has started")) {
                codpart = log.split("has")[0].split("match")[1].replace(" ", "");
                inicioDaPartida = log.split("-")[0];
            }
        }
        if (log.contains("killed")) {
            String player = log.split("killed")[0].split("-")[1].replace(" ", "");
            String playerMorto = retornaPlayerMorto(log);

            adicionaPlayerNoMapa(log, player);
            adicionaPlayerNoMapa(log, playerMorto);
            setPlayerNoMapa(log, player);
            setPlayerNoMapa(log, playerMorto);

        }
        
        if(log.contains("has ended")) {
            fimDaPartida = log.split("-")[0];
        }
    }

    public void adicionaPlayerNoMapa(String log, String player) {
        if (!mapPlayer.containsKey(player)) {
            mapPlayer.put(player, new Player(log, player));
        }
    }

    public void setPlayerNoMapa(String log, String player) {
        if (mapPlayer.containsKey(player)) {
            Player p = new Player(log,
                    player,
                    mapPlayer.get(player).getVitoriasList(),
                    mapPlayer.get(player).getDerrotasList(),
                    mapPlayer.get(player).getArma());
        }
    }

    public String retornaPlayerMorto(String log) {
        String ret = null;
        if (log.contains("by ")) {
            ret = log.split("killed ")[1].split(" by")[0];
        } else {
            ret = log.split("killed")[1].split(" using")[0].replace(" ", "");
        }
        return ret;
    }

    public String getCodpart() {
        return codpart;
    }

    public void setCodpart(String codpart) {
        this.codpart = codpart;
    }

    public Map<String, Player> getMapPlayer() {
        return mapPlayer;
    }

    public void setMapPlayer(Map<String, Player> mapPlayer) {
        this.mapPlayer = mapPlayer;
    }

    public String getInicioDaPartida() {
        return inicioDaPartida;
    }

    public void setInicioDaPartida(String inicioDaPartida) {
        this.inicioDaPartida = inicioDaPartida;
    }

    public String getFimDaPartida() {
        return fimDaPartida;
    }

    public void setFimDaPartida(String fimDaPartida) {
        this.fimDaPartida = fimDaPartida;
    }
    

}
