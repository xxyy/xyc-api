/*
 * MIT License
 *
 * Copyright (c) 2016-2017 Philipp Nowak (Literallie)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package li.l1t.lanatus.api.account;

import li.l1t.common.exception.DatabaseException;
import li.l1t.lanatus.api.LanatusRepository;
import li.l1t.lanatus.api.exception.AccountConflictException;

import java.util.Optional;
import java.util.UUID;

/**
 * A repository for Lanatus account information, providing both mutable and immutable objects.
 * <p>Mutable objects are regenerated for every call and may be changed at wish. Changes may be
 * written back to the database using the {@link #save(MutableAccount)} method. Note that mutable
 * accounts cannot be updated from the database since they might have been modified concurrently. A
 * fresh object must be obtained from this repository if an update is desired.</p> <p>Immutable
 * objects may not be changed and are not guaranteed to be regenerated at every call. However, their
 * data may change due to concurrent modifications by this client or another client. If an update is
 * required, it may be forced using {@link #refresh(AccountSnapshot)}.</p>
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface AccountRepository extends LanatusRepository {

    /**
     * Gets the current state of an account for mutation. If there is not et an account for given
     * player, an account initialised to the default values is returned. Note that the account does
     * not get created in the database immediately, but only when it is saved. <p><b>Note:</b> Try
     * to keep the scope for your mutable accounts as small as possible. See the {@link
     * MutableAccount} class JavaDoc for details.</p>
     *
     * @param playerId the unique id of the player whose account information to retrieve
     * @return the mutable account state
     * @throws DatabaseException if a database error occurs
     */
    MutableAccount findMutable(UUID playerId) throws DatabaseException;

    /**
     * Attempts to merge the state of given mutable account with its current state in the database.
     *
     * @param localCopy the local copy of the account to merge
     * @throws AccountConflictException if it is not possible to safely merge the changes into the
     *                                  database because it was concurrently modified
     * @throws DatabaseException        if a database error occurs
     */
    void save(MutableAccount localCopy) throws AccountConflictException, DatabaseException;

    /**
     * Optionally gets the a snapshot of an account for read-only purposes.
     *
     * @param playerId the unique id of the player whose account information to snapshot
     * @return an optional containing the snapshot of given player's account or an empty optional if
     * no account exists for given player
     * @throws DatabaseException if a database error occurs
     */
    Optional<AccountSnapshot> find(UUID playerId) throws DatabaseException;

    /**
     * Gets the a snapshot of an account for read-only purposes, or a snapshot of the defaults if
     * the account does not exist.
     *
     * @param playerId the unique id of the player whose account information to snapshot
     * @return an immutable snapshot of that player's account, or a snapshot of the defaults if
     * there is no account for given player
     * @throws DatabaseException if a database error occurs
     */
    AccountSnapshot findOrDefault(UUID playerId) throws DatabaseException;

    /**
     * Refreshes the state of an immutable account from the database and returns a new immutable
     * account with the latest state.
     *
     * @param account the account to refresh
     * @return a new account that represents the same player but with more recent data, or a
     * snapshot of the defaults if there is no account for given player
     * @throws DatabaseException if a database error occurs
     */
    AccountSnapshot refresh(AccountSnapshot account) throws DatabaseException;
}
