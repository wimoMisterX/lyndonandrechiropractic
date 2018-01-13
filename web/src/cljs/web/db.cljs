(ns web.db)

(def default-db
  {:active-panel :home-panel
   :contact-form-status nil
   :contact-form {:name {:value "" :valid nil}
                  :email-address {:value "" :valid nil}
                  :subject {:value "" :valid nil}
                  :message {:value "" :valid nil}}})
