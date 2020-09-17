package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.other.Cripting;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void registerUser(BlogUser user) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		try {
			JPAEMProvider.getEntityManager().createQuery("SELECT user from BlogUser as user where user.nick=:usr")
					.setParameter("usr", user.getNick()).getSingleResult();
		} catch (NoResultException ex) {
			entityManager.persist(user);
			return ;
		}
		throw new DAOException("User with that nickname already exists!");
	}

	@Override
	public BlogUser loginUser(String nick, String password) {
		BlogUser user;
		try {
			user = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("SELECT user from BlogUser as user where user.nick=:usr and user.passwordHash=:pw")
					.setParameter("usr", nick)
					.setParameter("pw", Cripting.hashPassword(password))
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
		return user;
	}

	@Override
	public BlogUser getUser(String nick) {
		try {
			return (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("SELECT user from BlogUser as user where user.nick=:usr")
					.setParameter("usr", nick)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public void insertNewEntry(BlogEntry blogEntry, Long creatorId) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		BlogUser creator = null;
		try {
			creator = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("SELECT user from BlogUser as user where user.id=:id")
					.setParameter("id", creatorId)
					.getSingleResult();
		}catch (NoResultException ex) {
			throw new DAOException("User does not exist");
		}
		blogEntry.setCreator(creator);
		entityManager.persist(blogEntry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getRegisteredUsers() {
		List<BlogUser> registeredUsers = new ArrayList<>();
		try {
			registeredUsers = (List<BlogUser>) JPAEMProvider.getEntityManager()
					.createQuery("SELECT user from BlogUser as user")
					.getResultList();
		}catch (NoResultException ex) {
			return registeredUsers;
		}
		return registeredUsers;
	}

	@Override
	public void updateBlogEntry(BlogEntry blogEntry) {
		JPAEMProvider.getEntityManager().merge(blogEntry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setUserEntries(Long id) {
		BlogUser user = null;
		try {
			user = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("SELECT user from BlogUser as user where user.id=:id")
					.setParameter("id", id)
					.getSingleResult();
			
			user.setUserEntries(
					(List<BlogEntry>) JPAEMProvider.getEntityManager()
					.createQuery("SELECT entry from BlogEntry as entry where creator_ID=:id")
					.setParameter("id", id)
					.getResultList()
					);
		}catch (NoResultException ex) {
			return;
		}
		return ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUserEntries(Long id) {
		List<BlogEntry> userEntries = new ArrayList<>();
		try {
			userEntries = (List<BlogEntry>) JPAEMProvider.getEntityManager()
					.createQuery("SELECT entry from BlogEntry as entry where creator_ID=:id")
					.setParameter("id", id)
					.getResultList();
		}catch (NoResultException ex) {
		}
		return userEntries;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogComment> getEntryComments(BlogEntry entry) {
		List<BlogComment> entryComments = new ArrayList<>();
		try {
			entryComments = (List<BlogComment>) JPAEMProvider.getEntityManager()
					.createQuery("SELECT comment from BlogComment as comment where blogEntry=:entry")
					.setParameter("entry", entry)
					.getResultList();
		}catch (NoResultException ex) {
		}
		return entryComments;
	}

	@Override
	public void setComment(BlogComment comment) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		entityManager.persist(comment);
	}
}