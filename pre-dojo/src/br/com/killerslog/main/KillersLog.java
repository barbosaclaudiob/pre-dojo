/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.killerslog.main;

import br.com.killerslog.model.Partida;
import br.com.killerslog.model.Player;
import br.com.killerslog.utils.KillersLogUtil;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class KillersLog {

    static Map<Integer, Map<String, String>> treeMapArmas = new TreeMap(
            new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    });

    /**
     * 
     * @param args 
     * 
     * Especifique o caminho do log na variavel path.
     */
    public static void main(String[] args) {
        String path = "/home/claudio/log";
        Partida p = KillersLogUtil.retornaObjetoPartida(path); 
        System.out.println("Partida numero " + p.getCodpart() + " iniciada às  " + p.getInicioDaPartida());
        imprimeRanking(p);
        System.out.println("==========================================");
        System.out.println("Partida numero " + p.getCodpart() + " encerrada às  " + p.getFimDaPartida());
        addArmaPreferida(p);
        System.out.println("");
        imprimeArmaPreferida();
        System.out.println("");
        imprimeJogadorQueNaoMorreu(p);

    }

    public static void imprimeJogadorQueNaoMorreu(Partida p) {
        Set<String> chaves = p.getMapPlayer().keySet();
        System.out.println("|----- Adiciona award por não ter morrido na partida para os Jogadores");
        for (String player : chaves) {
            if (p.getMapPlayer().get(player).getHoraDaMorte() == null) {
                System.out.println(p.getMapPlayer().get(player).getUsername());
            }
        }

    }

    public static void imprime5min(Partida p) {
        Set<String> chaves = p.getMapPlayer().keySet();
        Set<String> usuariosSobreviventes = new HashSet<>();
        for (String player : chaves) {
            for (Date dt : p.getMapPlayer().get(player).getVitoriasList()) {
                Calendar date = KillersLogUtil.dateToCalendar(dt);
                date.add(Calendar.MINUTE, 5);
                Date horaFim = date.getTime();
                if (dt.after(horaFim) && dt.before(horaFim)) {
                    usuariosSobreviventes.add(p.getMapPlayer().get(player).getUsername());
                }
            }
        }
        for (String usuariosSobrevivente : usuariosSobreviventes) {
            System.out.println(usuariosSobrevivente);
        }
    }

    public static void addArmaPreferida(Partida p) {
        Set<String> chaves = p.getMapPlayer().keySet();
        Map<String, String> mapMapUsuario = new HashMap<>();
        Integer key = null;
        for (String chave : chaves) {
            Player player = p.getMapPlayer().get(chave);
            String value = null;
            if (player.getArma() != null) {
                Set<String> armaSet = new HashSet<>(player.getArma());
                for (String s : armaSet) {
                    key = Collections.frequency(player.getArma(), s);
                    value = s;
                    mapMapUsuario.put(player.getUsername(), s);
                }
            }
        }
        if (key != null) {
            treeMapArmas.put(key, mapMapUsuario);
        }
    }

    public static void imprimeArmaPreferida() {
        System.out.println("|");
        System.out.println("==========> Armas Preferidas ");
        for (Map.Entry<Integer, Map<String, String>> entry : treeMapArmas.entrySet()) {
            Integer key = entry.getKey();
            Map<String, String> value = entry.getValue();
            System.out.println(value.toString().replace("}", "").replace("{", ""));
        }
    }

    public static void imprimeRanking(Partida p) {
        System.out.println("==========================================");

        Set<String> chaves = p.getMapPlayer().keySet();
        for (String chave : chaves) {

            if (!p.getMapPlayer().get(chave).getDerrotasList().isEmpty()) {
                Collections.sort(p.getMapPlayer().get(chave).getDerrotasList());
                p.getMapPlayer().get(chave).setHoraDaMorte(p.getMapPlayer().get(chave).getDerrotasList().get(0));
            }

            System.out.println(p.getMapPlayer().get(chave).getUsername() + " ----- " + KillersLogUtil.retornaLista(p.getMapPlayer().get(chave).getVitoriasList()) + "/" + p.getMapPlayer().get(chave).getDerrotasList().size());
        }
        
//        Estava trabalhando nesse método  com o atributo treeMapVitorias já populado pela partida para ordenar o raking....

//        for (Map.Entry<String, Map<String, Integer>> entry : treeMapVitorias.entrySet()) {
//            String key = entry.getKey();
//            Map<String, Integer> value = entry.getValue();
//            String player = value.toString().replace("=0}", "").replace("{", "");
//            Integer derrotas = null;
//            for (Map.Entry<String, Integer> entry1 : treeMapVitorias.get(key).entrySet()) {
//                player = entry1.getKey();
//                derrotas = entry1.getValue();
//            }
//            System.out.println(player + " ----- " + key + "/" + derrotas);
//        }

    }

}
