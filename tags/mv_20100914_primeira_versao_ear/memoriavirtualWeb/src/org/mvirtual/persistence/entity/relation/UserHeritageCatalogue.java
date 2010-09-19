package org.mvirtual.persistence.entity.relation;

import org.mvirtual.persistence.entity.relation.embedded.UserHeritageCatalogueId;

import org.mvirtual.persistence.entity.AbstractEntity;
import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.User;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * UserHeritageCatalogue model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`user_heritage_catalogue`")
public class UserHeritageCatalogue
	extends	AbstractEntity<UserHeritageCatalogueId>
	implements Serializable
{
	private static final long serialVersionUID = -5469290112813559973L;

	private UserHeritageCatalogueId id;
	private User user;
	private Heritage heritage;
	private String note;
	private Date startdate;
	private Date enddate;

	public UserHeritageCatalogue() {}

	public UserHeritageCatalogue(UserHeritageCatalogueId id, User user,
			Heritage heritage) {
		this.id = id;
		this.user = user;
		this.heritage = heritage;
	}

	public UserHeritageCatalogue(UserHeritageCatalogueId id, User user,
			Heritage heritage, String note, Date startdate, Date enddate) {
		this.id = id;
		this.user = user;
		this.heritage = heritage;
		this.note = note;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	@EmbeddedId
	public UserHeritageCatalogueId getId() {
		return this.id;
	}

	public void setId(UserHeritageCatalogueId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_user`", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_heritage`", nullable = false, insertable = false, updatable = false)
	public Heritage getHeritage() {
		return this.heritage;
	}

	public void setHeritage(Heritage heritage) {
		this.heritage = heritage;
	}

	@Column(name = "`note`")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "`startdate`", length = 13)
	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "`enddate`", length = 13)
	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String toString() {
		return this.getNote();
	}

	@Transient
	public String getRepr() {
		return "UserHeritageCatalogue";
	}
}
