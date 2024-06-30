package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);
        return getById(account.id()).isPresent();
    }

    public synchronized boolean update(Account account) {
        delete(account.id());
        add(account);
        return getById(account.id()).isPresent() && getById(account.id()).get().amount() == account.amount();
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (getById(fromId).isPresent() && getById(toId).isPresent()
                && getById(fromId).get().amount() > amount) {
            add(new Account(fromId, getById(fromId).get().amount() - amount));
            add(new Account(toId, getById(toId).get().amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}