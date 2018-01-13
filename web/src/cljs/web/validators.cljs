(ns web.validators
  (:require [clojure.string :as string]))

(defn falsey? [value]
  (or (nil? value) (false? value)))

(defn valid-email? [email]
  (let [pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"]
    (and (string? email) (not (nil? (re-matches pattern email))))))

(defn not-blank? [value]
  (not (string/blank? value)))

(def contact-form-validators {:email-address valid-email?
                              :name not-blank?
                              :subject not-blank?
                              :message not-blank?})
