(ns mini-banking.transfer
  (:require [mini-banking.accounts :refer [accounts]]))

(defn transfer [from to amount]
  (dosync
   (let [from-account (@accounts from)
         to-account (@accounts to)]
     (if (and from-account to-account)
       (if (>= (:balance from-account) amount)
         (do
           (alter accounts update-in [from :balance] - amount)
           (alter accounts update-in [to :balance] + amount))
         (throw (Exception. "Cannot transfer above account balances")))
       (throw (Exception. "One or both account doesn't exists"))))))