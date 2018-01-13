(ns web.components.form-input
  (:require [reagent.core :as r]
            [web.validators :refer [not-blank?]]))

(defn- update-cursor-focus [state focus? value?]
  (if (or value? focus?)
    (swap! state assoc :focused? true)
    (swap! state dissoc :focused?)))

(defn- get-form-field [field-type props]
  (cond
    (= field-type "textarea") [:textarea.materialize-textarea props]
    :else [:input (merge props {:type field-type})]))

(defn FormInput [label field-type props]
  (let [state (r/atom {:focused? (not-blank? (:value props))})]
    (fn [label field-type props]
      (let [value? (not-blank? (:value props))]
        [:div.input-field
         (get-form-field field-type (merge props {:on-focus #(update-cursor-focus state true value?)
                                                  :on-blur #(update-cursor-focus state  false value?)}))
         [:label {:for (:id props)
                  :class (if (:focused? @state) "active")}
          label]]))))
