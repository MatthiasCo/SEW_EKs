package E_6b_2.tgm.list;

import java.util.*;

import E_6b_2.tgm.list.exception.*;


/**
 * <p>Diese Klasse implementiert eine Benutzerdatenbasis.</p>
 * <p>Sie speichert eine Liste von Benutzern und erlaubt nur eindeutige Benutzernamen.</p>
 * @author Erhard List
 * @version 0.1 190319
 */
public class UserDatabase2 implements Iterable<User> {
    /**
     * speichert die ausgeteilte Version dieses Codes, damit für zukünftige Übungsaufgaben bei Codeänderungen
     * klar ist, welche Version verwendet wird. Lässt sich dynamisch abfragen. 
     */
		protected static final String VERSION = "1";
	
	private UserStorage<User> users;
	private User loggedInUser;
	
	/**
	 * <p>Erstellt eine neue Benutzerdatenbasis und fügt einen initialen Admin hinzu.</p>
	 * @param defaultadmin Gibt den ersten Benutzer der Benutzerdb an. Dies muss ein Benutzer mit der Rolle {@link E_6b_2.tgm.list.Role#ADMIN} sein,
	 *        da sonst keine weiteren Benutzer hinzugefügt werden könnten.
	 * @param store, der die Benutzer aufnehmen soll
	 * @throws IllegalArgumentException wird geworfen, wenn der übergebene Benutzer null oder keine {@link E_6b_2.tgm.list.Role#ADMIN} ist
	 */
	public UserDatabase2(User defaultadmin, UserStorage<User> store) 
	  throws IllegalArgumentException {
		users = store;
		if (defaultadmin == null || defaultadmin.getRole() != Role.ADMIN) throw new IllegalArgumentException("Default admin must be an user with the role ADMIN");
		users.store(defaultadmin.getUsername(),defaultadmin);
	}
	
	/**
	 * <p>Führt einen Login an der Benutzerdatenbasis aus. Ein bereits eingeloggter User wird dabei ausgeloggt</p>
	 * @param username Benutzer, der eingeloggt werden soll
	 * @param password Passwort des Benutzers
	 * @return true - wenn der Login erfolgreich war, false - wenn das Passwort falsch war
	 * @throws UserNotExistsException wird geworfen, wenn es den angegebenen Benutzernamen in der DB nicht gibt
	 */
	public boolean login(String username, String password) 
	  throws UserNotExistsException, NotAuthorizedException {
		User u = users.retrieve(username);
		logout();
		if (!u.verifyPassword(password)) return false;
		loggedInUser = u;
		return true;
	}
	
	/**
	 * <p>Loggt einen etwaig eingeloggten User aus</p>
	 */
	public void logout() {
		loggedInUser = null;
	}
	
	/**
	 * <p>Fügt einen neuen Benutzer der DB hinzu.</p>
	 * <p>Zusätzlich kann dem Benutzer ein Manager hinzugefügt werden. Dieser muss mindestens die Rolle {@link E_6b_2.tgm.list.Role#ADMIN} oder {@link E_6b_2.tgm.list.Role#MANAGER} haben. Was der Manager alles mit dem Benutzer
	 * machen darf siehe {@link E_6b_2.tgm.list.User}.</p>
	 * <p>Zum Hinzufügen eines Benutzers muss man mindestens die Rolle {@link E_6b_2.tgm.list.Role#MANAGER} haben.</p>
	 * @param username Benutzername des neuen Benutzers
	 * @param password Passwort des neuen Benutzers - wird nicht im Klartext gespeichert
	 * @param role Rolle des neuen Benutzers
	 * @param authorativeUser Manager-Benutzer, der dem neuen Benutzer zugeordnet wird
	 * @throws UserExistsException wird geworfen, wenn der Benutzername in der DB schon existiert
	 * @throws NotLoggedInException Benutzer können nur angelegt werden, wenn man an der DB angemeldet ist siehe {@link E_6b_2.tgm.list.UserDatabase2#login(String, String)}
	 * @throws NotEnoughPrivilegesException wird geworfen, wenn der angemeldete Benutzer nicht die Rechte zum Benutzer anlegen hat
	 *         oder wenn der übergebene Manager-Benutzer zu wenige Rechte hat.
	 */
	public void addUser(String username, String password, Role role, User authorativeUser) 
	  throws UserExistsException, NotLoggedInException,NotEnoughPrivilegesException {
		User u = new User(username,password,role,authorativeUser);
		addUser(u);
	}
	
	/**
	 * <p>Fügt einen neuen Benutzer der DB hinzu.</p>
	 * <p>Als Manager-Benutzer wird der gerade eingeloggte Benutzer hinzugefügt.</p>
	 * <p>Zum Hinzufügen eines Benutzers muss man mindestens die Rolle {@link E_6b_2.tgm.list.Role#MANAGER} haben.</p>
	 * @param username Benutzername des neuen Benutzers
	 * @param password Passwort des neuen Benutzers - wird nicht im Klartext gespeichert
	 * @param role Rolle des neuen Benutzers
	 * @throws UserExistsException wird geworfen, wenn der Benutzername in der DB schon existiert
	 * @throws NotLoggedInException Benutzer können nur angelegt werden, wenn man an der DB angemeldet ist siehe {@link E_6b_2.tgm.list.UserDatabase2#login(String, String)}
	 * @throws NotEnoughPrivilegesException wird geworfen, wenn der angemeldete Benutzer nicht die Rechte zum Benutzer anlegen hat
	 */
	public void addUser(String username, String password, Role role) 
	  throws UserExistsException, NotLoggedInException, NotEnoughPrivilegesException {
		addUser(username,password,role,this.loggedInUser);
	}
	
	/**
	 * <p>Fügt einen neuen Benutzer der DB hinzu.</p>
	 * <p>Als Manager-Benutzer wird der gerade eingeloggte Benutzer hinzugefügt.</p>
	 * <p>Zum Hinzufügen eines Benutzers muss man mindestens die Rolle {@link E_6b_2.tgm.list.Role#MANAGER} haben.</p>
	 * @param user Ein User-Objekt, dass den hinzuzufügenden Benutzer enthält
	 * @throws UserExistsException wird geworfen, wenn der Benutzername in der DB schon existiert
	 * @throws UserNotExistsException wenn der Manager-Benutzer nicht in der DB existiert
	 * @throws NotLoggedInException Benutzer können nur angelegt werden, wenn man an der DB angemeldet ist siehe {@link E_6b_2.tgm.list.UserDatabase2#login(String, String)}
	 * @throws NotEnoughPrivilegesException wird geworfen, wenn der angemeldete Benutzer nicht die Rechte zum Benutzer anlegen hat
	 */
	public void addUser(User user) 
	  throws UserExistsException, UserNotExistsException, NotLoggedInException,NotEnoughPrivilegesException {
		if (users.has(user.getUsername())) throw new UserExistsException("A user with that name already exists");
		if (!users.has(user.getAuthorativeUsername())) throw new UserNotExistsException("Authorative user must exist in DB");
		if (loggedInUser == null) throw new NotLoggedInException("No User logged in");
		if (loggedInUser.getRole().getLevel() > 1) throw new NotEnoughPrivilegesException("You are not allowed to add a user");
		users.store(user.getUsername(), user);
	}
	
	/**
	 * <p>Liefert alle Benutzernamen in der DB als Array zurück.</p>
	 * @return alle Benutzernamen in aufsteigend alphabetischer Reihenfolge.
	 */
	public String[] getAllUsernames() {
		String[] temp = users.retrieveAllIDs();
		Arrays.sort(temp);
		return temp;
	}
	
	/**
	 * <p>Löscht einen Benutzer aus der DB. Dazu muss man als {@link E_6b_2.tgm.list.Role#ADMIN} eingeloggt sein.</p>
	 * @param username Benutzername des Benutzers, der gelöscht werden soll
	 * @throws UserNotExistsException wird geworfen, wenn es den angegeben Benutzer nicht in der DB gibt
	 * @throws NotLoggedInException wenn kein Benutzer eingeloggt ist
	 * @throws NotEnoughPrivilegesException wenn der eingeloggte Benutzer kein {@link E_6b_2.tgm.list.Role#ADMIN} ist und daher das
	 *    Recht zu löschen nicht hat
	 * @throws IllegalArgumentException wenn der eingeloggte Benutzer sich selbst löschen will
	 */
	public void deleteUser(String username) 
	  throws UserNotExistsException, NotLoggedInException, NotEnoughPrivilegesException, IllegalArgumentException {
		if (!users.has(username)) throw new UserNotExistsException("User does not exist");
		if (loggedInUser == null) throw new NotLoggedInException("No User logged in");
		if ((loggedInUser.getRole() != Role.ADMIN) || 
			(!loggedInUser.getUsername().equals(users.retrieve(username).getAuthorativeUsername())))
		   throw new NotEnoughPrivilegesException("Logged in user doesn't have the right to delete this user");
		if (loggedInUser.getUsername().equals(username)) throw new IllegalArgumentException("User cannot delete himself");
		users.delete(username);
	}

	/**
	 * <p>Liefert einen Iterator zurück, mit dem durch alle Benutzer in der DB durchgegangen werden kann.</p>
	 * <p>Dafür muss man als {@link E_6b_2.tgm.list.Role#ADMIN} oder {@link E_6b_2.tgm.list.Role#MANAGER} eingeloggt sein.</p>
	 * siehe auch {@link java.lang.Iterable#iterator()}
	 * @return den Iterator
	 * @throws NotLoggedInException wenn kein Benutzer eingeloggt ist
	 * @throws NotEnoughPrivilegesException wenn der eingeloggte Benutzer kein Recht hat, die Benutzer einzusehen
	 */
	@Override
	public Iterator<User> iterator() 
	  throws NotLoggedInException, NotEnoughPrivilegesException {
		if (loggedInUser == null) throw new NotLoggedInException("No User logged in");
		if (loggedInUser.getRole().getLevel() > 1)
		   throw new NotEnoughPrivilegesException("Logged in user doesn't have the right to do this");
		return users.iterator();
	}
	
}
