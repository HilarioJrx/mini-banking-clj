(ns mini-banking.core)

(def accounts (ref {}))

(defn create-account [id initial-balance]
    (dosync
        (if (contains? @accounts id)
            (throw (Exception. "Account already exists"))
            (alter accounts assoc id {:balance initial-balance}))))

(defn get-balance [id]
    (dosync
        (if-let [account (@accounts id)]
            (:balance account)
            (throw (Exception. "Account not found")))))

(defn get-accounts []
    (dosync 
        @accounts))
