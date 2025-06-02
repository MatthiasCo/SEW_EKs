package E_6b_2.tgm.list;
import E_6b_2.tgm.list.exception.UserNotExistsException;

/**
 * <p>Interface für einen Benutzerspeicher for tgm.list.User-Elemente.</p>
 * <p>Jedes User-Element wird mit einer eindeutigen Text-ID gespeichert und kann über diese
 * abgerufen werden.</p>
 * @author Erhard List
 * @version 0.1 190425
 */
public interface UserStorage<T extends User> extends Iterable<T> {
	
	public static final String VERSION = "1";
	
	/**
	 * <p>Speichert ein neues User-Element im Speicher.<p>
	 * <p>Ist die gleiche User-ID schon vorhanden, wird das Element nicht gespeichert und false zurückgegeben</p>
	 * @param userid ID unter der das User-Element gespeichert werden soll. Darf für eine erfolgreiche Speicherung nicht im Speicher vorhanden sein.
	 * @param user User-Element, das gespeichert werden soll.
	 * @return true - wenn erfolgreich gespeichert wurde, sonst false
	 */
	public boolean store(String userid, T user);
	
	/**
	 * <p>Prüft, ob ein User-Element mit der angegebenen ID gespeichert ist</p>
	 * @param userid zu prüfende ID
	 * @return true - wenn das User-Element im Store vorhanden ist, sonst false
	 */
	public boolean has(String userid);
	
	/**
	 * <p>Liest das User-Element mit der angegebenen ID aus dem User-Speicher</p>
	 * @param userid die gesuchte User-ID
	 * @return das zur ID gehörige User-Element
	 * @throws UserNotExistsException wird geworfen, wenn die userid im User-Speicher nicht existiert.
	 */
	public T retrieve(String userid) throws UserNotExistsException;
	
	/**
	 * <p>Liest alle im User-Speicher vorhandenen IDs aus und gibt sie zurück</p>
	 * @return alle IDs des User-Speichers
	 */
	public String[] retrieveAllIDs();
	
	/**
	 * <p>Löscht ein User-Element aus dem User-Speicher, falls es vorhanden ist.
	 * @param userid die ID des zu löschenden User-Elements
	 * @return true - wenn das User-Element gelöscht werden konnte, sonst false.
	 */
	public boolean delete(String userid);
	
}
