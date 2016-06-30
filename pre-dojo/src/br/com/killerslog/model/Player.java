/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.killerslog.model;

import br.com.killerslog.utils.KillersLogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author claudio
 */
public class Player {

    private String username;
    private List<Date> vitoriasList;
    private List<Date> derrotasList;
    private List<String> arma;
    private Date horaDaMorte;

    public Player(String log, String player) {
        this.vitoriasList = new ArrayList<>();
        this.derrotasList = new ArrayList<>();
        setUsername(player);
        adicionaArma(log, player);
        addVitoriaOuDerrota(log, player);

    }

    public Player() {

    }

    public Player(String log, String username, List<Date> vitoriasList, List<Date> derrotasList, List<String> arma) {
        this.username = username;
        this.vitoriasList = vitoriasList;
        this.derrotasList = derrotasList;
        this.arma = arma;
        adicionaArma(log, username);
        addVitoriaOuDerrota(log, username);
    }

    public void adicionaArma(String log, String player) {
        if (log.contains(player + " killed")) {
            if (getArma() == null) {
                setArma(new ArrayList<>());
            }
            if (log.contains("using")) {
                getArma().add(log.split("using")[1].replace(" ", ""));
            } else {
                getArma().add(log.split("by")[1].replace(" ", ""));
            }
        }
    }

    public void addVitoriaOuDerrota(String log, String player) {
        String data = log.split(" -")[0];
        Date dt = KillersLogUtil.retornaStringToDate(data);
        if (log.contains("killed " + getUsername())) {
            this.derrotasList.add(dt);
        } else if (log.contains(getUsername() + " killed")) {
            if (!getUsername().equals("<WORLD>")) {
                this.vitoriasList.add(dt);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Date> getVitoriasList() {
        return vitoriasList;
    }

    public void setVitoriasList(List<Date> vitoriasList) {
        this.vitoriasList = vitoriasList;
    }

    public List<Date> getDerrotasList() {
        return derrotasList;
    }

    public void setDerrotasList(List<Date> derrotasList) {
        this.derrotasList = derrotasList;
    }

    public List<String> getArma() {
        return arma;
    }

    public void setArma(List<String> arma) {
        this.arma = arma;
    }

    public Date getHoraDaMorte() {
        return horaDaMorte;
    }

    public void setHoraDaMorte(Date horaDaMorte) {
        this.horaDaMorte = horaDaMorte;
    }

}
