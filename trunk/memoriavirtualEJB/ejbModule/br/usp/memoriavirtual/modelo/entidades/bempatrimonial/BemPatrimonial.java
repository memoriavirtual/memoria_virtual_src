package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "BEMPATRIMONIAL_ID", sequenceName = "BEMPATRIMONIAL_SEQ", allocationSize = 1)
public class BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3790813768470746267L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEMPATRIMONIAL_ID")
	protected long id;
	
	
	

	
}


