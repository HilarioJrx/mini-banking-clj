(ns mini-banking.accounts)

(def accounts (ref {}))

(defn create-account [id initial-balance]
  (dosync
   (if (contains? @accounts id)
     (throw (Exception. "Account already exists"))
     (alter accounts assoc id {:balance initial-balance}))))

(defn get-accounts []
  (dosync
   @accounts))

(defn get-balance [id]
  (dosync
   (if-let [account (@accounts id)]
     (:balance account)
     (throw (Exception. "Account not found")))))

(defn deposit [id amount]
  (dosync
   (if (contains? @accounts id)
     (alter accounts update-in [id :balance] + amount)
     (throw (Exception. (str "Account " id " not found"))))))

(defn withdraw [id amount]
  (dosync
   (if-let [account (@accounts id)]
     (if (>= (:balance account) amount)
       (alter accounts update-in [id :balance] - amount)
       (throw (Exception. "Cannot withdraw above account balances")))
     (throw (Exception. (str "Account " id " not found"))))))
