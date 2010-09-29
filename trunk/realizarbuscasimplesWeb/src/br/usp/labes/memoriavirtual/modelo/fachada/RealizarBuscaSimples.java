package br.usp.labes.memoriavirtual.modelo.fachada;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import br.usp.labes.memoriavirtual.database.Database;

public class RealizarBuscaSimples {
	
	public BemPatrimonial bemPatrimonial(String id) throws Exception {
		Database db = new Database();
		BemPatrimonial bempatrimonial = new BemPatrimonial();
		ResultSet rs = findByPrimaryKey(db, id);
		
		if (rs.next()) {
			
			bempatrimonial.setId(rs.getString("id"));
			bempatrimonial.setId_institution(rs.getString("id_institution"));
			bempatrimonial.setTitle(rs.getString("title"));
			bempatrimonial.setAccessconditions(rs.getString("accessconditions"));
			bempatrimonial.setAcquisitioncurrentowner(rs.getString("acquisitioncurrentowner"));
			bempatrimonial.setAcquisitiondate(rs.getString("acquisitiondate"));
			bempatrimonial.setAcquisitionorigin(rs.getString("acquisitionorigin"));
			bempatrimonial.setAcquisitiontype(rs.getString("acquisitiontype"));
			bempatrimonial.setAcquisitionvalue(rs.getString("acquisitionvalue"));
			bempatrimonial.setAlternativetitle(rs.getString("alternativetitle"));
			bempatrimonial.setCollection(rs.getString("collection"));
			bempatrimonial.setComplementtitle(rs.getString("complementtitle"));
			bempatrimonial.setCondition(rs.getString("condition"));
			bempatrimonial.setConditionnotes(rs.getString("conditionnotes"));
			bempatrimonial.setContent(rs.getString("Content"));
			bempatrimonial.setControlnumber(rs.getString("controlnumber"));
			bempatrimonial.setDate(rs.getString("date"));
			bempatrimonial.setDimensions(rs.getString("dimensions"));
			bempatrimonial.setAditionnumber(rs.getString("aditionnumber"));
			bempatrimonial.setFindingaid(rs.getString("findingaid"));
			bempatrimonial.setGps_latitude(rs.getString("gps_latitude"));
			bempatrimonial.setGps_longitude(rs.getString("gps_longitude"));
			bempatrimonial.setHeritageprotectioninstitution(rs.getString("heritageprotectioninstitution"));
			bempatrimonial.setHistoric(rs.getString("historic"));
			bempatrimonial.setLegislation(rs.getString("legislation"));
			bempatrimonial.setLocal(rs.getString("local"));
			bempatrimonial.setNote(rs.getString("note"));
			bempatrimonial.setOriginaltitle(rs.getString("originaltitle"));
			bempatrimonial.setOtherresponsibilities(rs.getString("otherresponsibilities"));
			bempatrimonial.setPhysicalfeatures(rs.getString("physicalfeatures"));
			bempatrimonial.setPhysicallocation(rs.getString("physicallocation"));
			bempatrimonial.setProtection(rs.getString("protection"));
			bempatrimonial.setRegistrynumber(rs.getString("registrynumber"));
			bempatrimonial.setReissuenumber(rs.getString("reissuenumber"));
			bempatrimonial.setReproductionconditions(rs.getString("reproductionconditions"));
			bempatrimonial.setReviewed(rs.getString("reviewed"));
			bempatrimonial.setSituation(rs.getString("situation"));
			bempatrimonial.setSupport(rs.getString("support"));
			bempatrimonial.setUsage(rs.getString("usage"));
			
			return bempatrimonial;
		}
		db.close();
		
		return null;
	}
	
	public static List<BemPatrimonial> realizarBusca(String id) throws Exception{
		Database db = new Database();
		BemPatrimonial bempatrimonial = null;
		List<BemPatrimonial> ListaBemPatrimonial = new ArrayList<BemPatrimonial>();
		
		ResultSet rs = findByLike(db, id);
		
		if (rs.next()) {
			bempatrimonial = new BemPatrimonial();
			
			bempatrimonial.setId(rs.getString("id"));
			bempatrimonial.setId_institution(rs.getString("id_institution"));
			bempatrimonial.setTitle(rs.getString("title"));
			bempatrimonial.setAccessconditions(rs.getString("accessconditions"));
			bempatrimonial.setAcquisitioncurrentowner(rs.getString("acquisitioncurrentowner"));
			bempatrimonial.setAcquisitiondate(rs.getString("acquisitiondate"));
			bempatrimonial.setAcquisitionorigin(rs.getString("acquisitionorigin"));
			bempatrimonial.setAcquisitiontype(rs.getString("acquisitiontype"));
			bempatrimonial.setAcquisitionvalue(rs.getString("acquisitionvalue"));
			bempatrimonial.setAlternativetitle(rs.getString("alternativetitle"));
			bempatrimonial.setCollection(rs.getString("collection"));
			bempatrimonial.setComplementtitle(rs.getString("complementtitle"));
			bempatrimonial.setCondition(rs.getString("condition"));
			bempatrimonial.setConditionnotes(rs.getString("conditionnotes"));
			bempatrimonial.setContent(rs.getString("Content"));
			bempatrimonial.setControlnumber(rs.getString("controlnumber"));
			bempatrimonial.setDate(rs.getString("date"));
			bempatrimonial.setDimensions(rs.getString("dimensions"));
			bempatrimonial.setAditionnumber(rs.getString("aditionnumber"));
			bempatrimonial.setFindingaid(rs.getString("findingaid"));
			bempatrimonial.setGps_latitude(rs.getString("gps_latitude"));
			bempatrimonial.setGps_longitude(rs.getString("gps_longitude"));
			bempatrimonial.setHeritageprotectioninstitution(rs.getString("heritageprotectioninstitution"));
			bempatrimonial.setHistoric(rs.getString("historic"));
			bempatrimonial.setLegislation(rs.getString("legislation"));
			bempatrimonial.setLocal(rs.getString("local"));
			bempatrimonial.setNote(rs.getString("note"));
			bempatrimonial.setOriginaltitle(rs.getString("originaltitle"));
			bempatrimonial.setOtherresponsibilities(rs.getString("otherresponsibilities"));
			bempatrimonial.setPhysicalfeatures(rs.getString("physicalfeatures"));
			bempatrimonial.setPhysicallocation(rs.getString("physicallocation"));
			bempatrimonial.setProtection(rs.getString("protection"));
			bempatrimonial.setRegistrynumber(rs.getString("registrynumber"));
			bempatrimonial.setReissuenumber(rs.getString("reissuenumber"));
			bempatrimonial.setReproductionconditions(rs.getString("reproductionconditions"));
			bempatrimonial.setReviewed(rs.getString("reviewed"));
			bempatrimonial.setSituation(rs.getString("situation"));
			bempatrimonial.setSupport(rs.getString("support"));
			bempatrimonial.setUsage(rs.getString("usage"));
			
			ListaBemPatrimonial.add(bempatrimonial);
			
			return ListaBemPatrimonial;
		}
		
		db.close();
		
		return null;	
	}
	
	public static List<BemPatrimonial> Patrimonial() throws Exception{
		
		Database db = new Database();
		BemPatrimonial bempatrimonial = null;
		List<BemPatrimonial> ListaBemPatrimonial = new ArrayList<BemPatrimonial>();
		
		ResultSet rs = findAll(db);
		
		if (rs.next()) {
			bempatrimonial = new BemPatrimonial();
			
			bempatrimonial.setId(rs.getString("id"));
			bempatrimonial.setId_institution(rs.getString("id_institution"));
			bempatrimonial.setTitle(rs.getString("title"));
			bempatrimonial.setAccessconditions(rs.getString("accessconditions"));
			bempatrimonial.setAcquisitioncurrentowner(rs.getString("acquisitioncurrentowner"));
			bempatrimonial.setAcquisitiondate(rs.getString("acquisitiondate"));
			bempatrimonial.setAcquisitionorigin(rs.getString("acquisitionorigin"));
			bempatrimonial.setAcquisitiontype(rs.getString("acquisitiontype"));
			bempatrimonial.setAcquisitionvalue(rs.getString("acquisitionvalue"));
			bempatrimonial.setAlternativetitle(rs.getString("alternativetitle"));
			bempatrimonial.setCollection(rs.getString("collection"));
			bempatrimonial.setComplementtitle(rs.getString("complementtitle"));
			bempatrimonial.setCondition(rs.getString("condition"));
			bempatrimonial.setConditionnotes(rs.getString("conditionnotes"));
			bempatrimonial.setContent(rs.getString("Content"));
			bempatrimonial.setControlnumber(rs.getString("controlnumber"));
			bempatrimonial.setDate(rs.getString("date"));
			bempatrimonial.setDimensions(rs.getString("dimensions"));
			bempatrimonial.setAditionnumber(rs.getString("aditionnumber"));
			bempatrimonial.setFindingaid(rs.getString("findingaid"));
			bempatrimonial.setGps_latitude(rs.getString("gps_latitude"));
			bempatrimonial.setGps_longitude(rs.getString("gps_longitude"));
			bempatrimonial.setHeritageprotectioninstitution(rs.getString("heritageprotectioninstitution"));
			bempatrimonial.setHistoric(rs.getString("historic"));
			bempatrimonial.setLegislation(rs.getString("legislation"));
			bempatrimonial.setLocal(rs.getString("local"));
			bempatrimonial.setNote(rs.getString("note"));
			bempatrimonial.setOriginaltitle(rs.getString("originaltitle"));
			bempatrimonial.setOtherresponsibilities(rs.getString("otherresponsibilities"));
			bempatrimonial.setPhysicalfeatures(rs.getString("physicalfeatures"));
			bempatrimonial.setPhysicallocation(rs.getString("physicallocation"));
			bempatrimonial.setProtection(rs.getString("protection"));
			bempatrimonial.setRegistrynumber(rs.getString("registrynumber"));
			bempatrimonial.setReissuenumber(rs.getString("reissuenumber"));
			bempatrimonial.setReproductionconditions(rs.getString("reproductionconditions"));
			bempatrimonial.setReviewed(rs.getString("reviewed"));
			bempatrimonial.setSituation(rs.getString("situation"));
			bempatrimonial.setSupport(rs.getString("support"));
			bempatrimonial.setUsage(rs.getString("usage"));
			
			ListaBemPatrimonial.add(bempatrimonial);
			
			return ListaBemPatrimonial;
		}
		
		db.close();
		
		return null;	
	}
	
	public static BemPatrimonial next(ResultSet rs) throws Exception {
		if (rs.next()) {
			BemPatrimonial busca = new BemPatrimonial();
			
			busca.setId(rs.getString("id"));
			busca.setId_institution(rs.getString("id_institution"));
			busca.setTitle(rs.getString("title"));
			busca.setAccessconditions(rs.getString("accessconditions"));
			busca.setAcquisitioncurrentowner(rs.getString("acquisitioncurrentowner"));
			busca.setAcquisitiondate(rs.getString("acquisitiondate"));
			busca.setAcquisitionorigin(rs.getString("acquisitionorigin"));
			busca.setAcquisitiontype(rs.getString("acquisitiontype"));
			busca.setAcquisitionvalue(rs.getString("acquisitionvalue"));
			busca.setAlternativetitle(rs.getString("alternativetitle"));
			busca.setCollection(rs.getString("collection"));
			busca.setComplementtitle(rs.getString("complementtitle"));
			busca.setCondition(rs.getString("condition"));
			busca.setConditionnotes(rs.getString("conditionnotes"));
			busca.setContent(rs.getString("Content"));
			busca.setControlnumber(rs.getString("controlnumber"));
			busca.setDate(rs.getString("date"));
			busca.setDimensions(rs.getString("dimensions"));
			busca.setAditionnumber(rs.getString("aditionnumber"));
			busca.setFindingaid(rs.getString("findingaid"));
			busca.setGps_latitude(rs.getString("gps_latitude"));
			busca.setGps_longitude(rs.getString("gps_longitude"));
			busca.setHeritageprotectioninstitution(rs.getString("heritageprotectioninstitution"));
			busca.setHistoric(rs.getString("historic"));
			busca.setLegislation(rs.getString("legislation"));
			busca.setLocal(rs.getString("local"));
			busca.setNote(rs.getString("note"));
			busca.setOriginaltitle(rs.getString("originaltitle"));
			busca.setOtherresponsibilities(rs.getString("otherresponsibilities"));
			busca.setPhysicalfeatures(rs.getString("physicalfeatures"));
			busca.setPhysicallocation(rs.getString("physicallocation"));
			busca.setProtection(rs.getString("protection"));
			busca.setRegistrynumber(rs.getString("registrynumber"));
			busca.setReissuenumber(rs.getString("reissuenumber"));
			busca.setReproductionconditions(rs.getString("reproductionconditions"));
			busca.setReviewed(rs.getString("reviewed"));
			busca.setSituation(rs.getString("situation"));
			busca.setSupport(rs.getString("support"));
			busca.setUsage(rs.getString("usage"));
			
			return busca;
		}
		return null;
	}
	
	public static ResultSet findAll(Database db) throws Exception {
		return db.query("select * from heritage");
	}
	public static ResultSet findByPrimaryKey(Database db,
			String id) throws Exception {
		return db.query("select * from heritage where id = "+id);
	}
	
	public static ResultSet findByLike(Database db,
			String id) throws Exception {
		return db.query("select * from heritage where id like '%"+id+"%'");
	}
		
}
