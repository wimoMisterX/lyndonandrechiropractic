(ns web.views
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [clojure.string :as string]

            [web.utils :refer [md5-hex]]
            [web.constants :as constants]
            [web.components.form-input :refer [FormInput]]))

;; helpers

(defn paragraph [para]
  [:p.flow-text.left-align {:key (md5-hex para)} para])

(defn class-for-validity [valid]
  (cond
    (true? valid) "valid"
    (false? valid) "invalid"
    :else ""))

(defn font-awesome-icon-class [icon]
  (let [icon-name (if (keyword? icon) (name icon) icon)]
    (str "fa-" icon-name)))

(defn form-field [form-prefix form-change-ev input-type field-kw label]
  (let [field-name (name field-kw)
        {value :value valid :valid} @(rf/subscribe [(keyword (str form-prefix field-name))])]
    [FormInput label input-type {:id field-name
                                 :class (class-for-validity valid)
                                 :value value
                                 :on-change #(rf/dispatch [form-change-ev field-kw (-> % .-target .-value)])}]))


;; home

(defn home-panel []
  [:div.row
   [:div.col.s12.center-align [:img.responsive-img {:src constants/home-profile-picture}]]
   [:div.col.s12.center-align [:h3 constants/full-name]]
   [:div.col.s12.center-align [:h4.job-title constants/job-title]]
   [:div.col.s12.center-align [:h5 constants/academic-titles]]
   [:div.col.s12.center-align [:p.flow-text constants/summary]]])


;; about

(defn about-panel []
  [:div (map paragraph constants/about-content)])


;; experience

(defn experience-panel []
  [:div (map paragraph constants/experience-content)])


;; contact

(defn contact-form-submit-button []
  (let [form-status @(rf/subscribe [:contact-form-status])]
    [:button.btn.waves-effect.waves-light {:on-click #(rf/dispatch [:contact-form-submit])
                                           :disabled (not (nil? form-status))}
     (cond
       (= form-status :submitting) [:span "Sending" [:i.fas.right.fa-circle-notch.fa-spin]]
       (= form-status :submitted) [:span "Your message has been sent" [:i.fas.right.fa-check]]
       :else [:span "Send" [:i.fas.right.fa-paper-plane]])]))

(defn contact-form []
  (let [render-form-field (partial form-field "contact-form-" :contact-form-value-change)
        form-status @(rf/subscribe [:contact-form-status])]
    [:div
     [:h4 "Please get in touch..."]
     (render-form-field "text" :name "Name")
     (render-form-field "email" :email-address "Email Address")
     (render-form-field "text" :subject "Subject")
     (render-form-field "textarea" :message "Message")
     (contact-form-submit-button)]))

(defn personal-contact-row [[icon-kw content]]
  [:div.row.personal-contact {:key icon-kw}
   [:div.col.s2 [:i.fas.fa-2x {:class (font-awesome-icon-class icon-kw)}]]
   [:div.col.s10 (map paragraph content)]])

(defn social-contact [[icon-kw url]]
  [:div.col.s2.social-contact {:key icon-kw}
   [:a {:href url}
   [:i.fab.fa-2x {:class (font-awesome-icon-class icon-kw)}]]])

(defn contact-methods []
  [:div
    (map personal-contact-row constants/personal-contacts)
    [:div.row.social-row
     (map social-contact constants/social-media)]])

(defn contact-panel []
  [:div.row
   [:div.col.s12.m5.contact-form (contact-form)]
   [:div.col.s12.m2.spacer
    [:div.valign-wrapper.hide-on-med-and-down
     [:div.blue-grey.lighten-3e]]]
   [:div.col.s12.m5.contact-methods (contact-methods)]])


;; navigation

(def panel-to-details
  (array-map :home-panel {:path "#/" :label "Home" :component home-panel}
             :about-panel {:path "#/about" :label "About" :component about-panel}
             :experience-panel {:path "#/experience" :label "Experience" :component experience-panel}
             :contact-panel {:path "#/contact" :label "Contact" :component contact-panel}))

(defn nav-item [active-panel [panel-kw {href :path label :label}]]
  [:li {:class (if (= active-panel panel-kw) "active") :key label}
   [:a {:href href} label]])

(defn nav-panel [active-panel]
  [:div.navbar-fixed
   [:nav.blue-grey.lighten-3e
    [:div.nav-wrapper
     [:ul.center
      (map (partial nav-item active-panel) panel-to-details)]]]])


;; footer

(defn footer []
  [:footer.page-footer.blue-grey.lighten-3e
   [:div.footer-copyright
    [:div.container "© 2018 Copyright"]]])


;; main

(defn main-panel []
  (let [active-panel @(rf/subscribe [:active-panel])]
    [:div
     (nav-panel active-panel)
     [:div.container
      [:div {:class (name active-panel)}
       [(:component (get panel-to-details active-panel {:component [:div]}))]]]
     [footer]]))
