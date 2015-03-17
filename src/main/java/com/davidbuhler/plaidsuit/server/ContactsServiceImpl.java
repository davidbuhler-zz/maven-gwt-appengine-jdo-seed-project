package com.davidbuhler.plaidsuit.server;

import com.davidbuhler.plaidsuit.server.entity.Contact;
import com.davidbuhler.plaidsuit.shared.constants.UserMessages;
import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.davidbuhler.plaidsuit.shared.exceptions.BroadCastableException;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreTimeoutException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;


@SuppressWarnings("serial")
public class ContactsServiceImpl extends RemoteServiceServlet implements com.davidbuhler.plaidsuit.client.ContactsService
{

    private static final Logger LOG = LoggerFactory.getLogger(ContactsServiceImpl.class.getName());
    private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    public ContactsServiceImpl() {
    }

    public ContactDTO saveContact(ContactDTO contactDTO) throws BroadCastableException {
        PersistenceManager pm = PMF.getPersistenceManager();
        Contact contact;
        try {
            if (contactDTO.getId() == null) {
                contact = new Contact();
            } else {
                contact = queryContact(contactDTO.getId(), pm);
            }
            pm.currentTransaction().begin();
            contact.setFirstName(contactDTO.getFirstName());
            contact.setLastName(contactDTO.getLastName());
            final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            final Set<ConstraintViolation<Contact>> violations = validator.validate(contact, Default.class);
            if (!violations.isEmpty()) {
                LOG.warn(Level.WARNING.getName(), violations.iterator().next().getMessage());
                throw new BroadCastableException(violations.iterator().next().getMessage());
            }
            pm.makePersistent(contact);
            pm.flush();
            pm.currentTransaction().commit();
        } catch (BroadCastableException ex) {
            LOG.warn(Level.WARNING.getName(), ex);
            throw ex;
        } catch (Exception ex) {
            LOG.warn(Level.SEVERE.getName(), ex);
            throw new BroadCastableException(UserMessages.AN_UNEXPECTED_ERROR_HAS_TAKEN_PLACE);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            pm.close();
        }
        return contactDTO;
    }

    public Boolean deleteContact(String id) throws BroadCastableException {
        PersistenceManager pm = PMF.getPersistenceManager();
        pm.currentTransaction().begin();
        try {
            Contact contact = queryContact(id, pm);
            pm.deletePersistent(contact);
            pm.currentTransaction().commit();
        } catch (JDOObjectNotFoundException ex) {
            LOG.error(Level.SEVERE.getName(), ex);
            throw new BroadCastableException(UserMessages.ITEM_NOT_FOUND);
        } catch (DatastoreTimeoutException | ConcurrentModificationException | DatastoreFailureException ex) {
            LOG.warn(Level.WARNING.getName(), ex);
        } catch (Exception ex) {
            LOG.warn(Level.SEVERE.getName(), ex);
            throw new BroadCastableException(UserMessages.AN_UNEXPECTED_ERROR_HAS_TAKEN_PLACE);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            pm.close();
        }
        return true;
    }

    public ContactDTO getContact(String id) throws BroadCastableException {
        PersistenceManager pm = PMF.getPersistenceManager();
        Contact contact;
        ContactDTO contactDTO;
        try {
            contact = queryContact(id, pm);
            contactDTO = convertToDTO(contact);
        } catch (JDOObjectNotFoundException ex) {
            LOG.warn(Level.WARNING.getName(), ex);
            throw new BroadCastableException(UserMessages.ITEM_NOT_FOUND);
        } catch (DatastoreTimeoutException | ConcurrentModificationException | DatastoreFailureException ex) {
            LOG.warn(Level.WARNING.getName(), ex);
            throw ex;
        } catch (Exception ex) {
            LOG.warn(Level.WARNING.getName(), ex);
            throw new BroadCastableException(UserMessages.AN_UNEXPECTED_ERROR_HAS_TAKEN_PLACE);
        } finally {
            pm.close();
        }
        return contactDTO;
    }

    public List<ContactDTO> listContacts() throws BroadCastableException {
        ArrayList<ContactDTO> contactDTOs = new ArrayList<>();
        PersistenceManager pm = PMF.getPersistenceManager();
        Query query = null;
        try {
            query = pm.newQuery(Contact.class);
            List<Contact> results = (List<Contact>) query.execute(20);
            if (!results.isEmpty()) {
                for (Contact contact : results) {
                    ContactDTO contactDTO = convertToDTO(contact);
                    contactDTOs.add(contactDTO);
                }
            }
        } catch (Exception ex) {
            LOG.warn(Level.WARNING.getName(), ex);
            throw new BroadCastableException(UserMessages.AN_UNEXPECTED_ERROR_HAS_TAKEN_PLACE);
        } finally {
            if (query != null) {
                query.closeAll();
            }
            pm.close();
        }
        return contactDTOs;
    }

    private Contact queryContact(String id, PersistenceManager pm) {
        Contact contact;
        try {
            Key key = KeyFactory.stringToKey(id);
            contact = pm.getObjectById(Contact.class, key.getId());
        } catch (Exception ex) {
            LOG.warn(Level.WARNING.getName(), ex);
            throw ex;
        }
        return contact;
    }

    private static ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO;
        contactDTO = new ContactDTO();
        contactDTO.setId(KeyFactory.keyToString(contact.getContactKey()));
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        return contactDTO;
    }
}
