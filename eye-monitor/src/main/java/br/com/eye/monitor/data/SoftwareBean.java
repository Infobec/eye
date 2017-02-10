package br.com.eye.monitor.data;

import br.com.eye.data.SonarData;

import java.util.*;

import org.springframework.util.StringUtils;

public class SoftwareBean {

    private static final int MAX_ITENS = 100;
	private Map<String, List<SonarData>> funcionalidade = new HashMap<String, List<SonarData>>();
    private Map<String, List<String>> erros = new HashMap<String, List<String>>(); // funciondadlei - versao
    private Map<String, Map<String, CountVersion>> versoes = new TreeMap<String, Map<String, CountVersion>>(); // sistema - versao
    private Map<String, Boolean> clients =  new TreeMap<String, Boolean>();
    
    private int nroFuncionalidades;
    private int nroErros;

    public void add(SonarData sonarData) {

        // validando as versoes
        if( !versoes.containsKey(sonarData.getVersion()) ){
            versoes.put(sonarData.getVersion(), new HashMap<String, CountVersion>());
        }

        Map<String, CountVersion> versao = versoes.get(sonarData.getVersion());
        // validando as funconadliades
        if( !versao.containsKey(sonarData.getDescription())){
            versao.put(sonarData.getDescription(), new CountVersion());
        }

        versao.get(sonarData.getDescription()).addTotal();
        
        
        if( sonarData.isError() ){
            if( !erros.containsKey(sonarData.getDescription()) ){
                erros.put(sonarData.getDescription(), new ArrayList<String>());
            }
            erros.get(sonarData.getDescription()).add(sonarData.getException());
            nroErros++;
            versao.get(sonarData.getDescription()).addError();
        }

        if( !funcionalidade.containsKey(sonarData.getDescription()) ){
            funcionalidade.put(sonarData.getDescription(), new ArrayList<SonarData>());
        }

        List<SonarData> itens = funcionalidade.get(sonarData.getDescription());
        if( itens.size() >= MAX_ITENS){
        	itens.remove(0);
        	itens.add(sonarData);
        }
        
        nroFuncionalidades++;
        
        if( !StringUtils.isEmpty(sonarData.getClient()) ){
        	clients.put(sonarData.getClient(), true);
        }
        
    }

    public Map<String, List<SonarData>> getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(Map<String, List<SonarData>> funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public Map<String, List<String>> getErros() {
        return erros;
    }

    public void setErros(Map<String, List<String>> erros) {
        this.erros = erros;
    }


    public String getNroFuncionalidades() {
        return nroFuncionalidades+"";
    }

    public void setNroFuncionalidades(int nroFuncionalidades) {
        this.nroFuncionalidades = nroFuncionalidades;
    }

    public String getNroErros() {
        return nroErros+"";
    }

    public void setNroErros(int nroErros) {
        this.nroErros = nroErros;
    }

	public Map<String, Map<String, CountVersion>> getVersoes() {
		return versoes;
	}

	public void setVersoes(Map<String, Map<String, CountVersion>> versoes) {
		this.versoes = versoes;
	}

	public Map<String, Boolean> getClients() {
		return clients;
	}


}
