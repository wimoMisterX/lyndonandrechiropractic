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
   [:div.col.s12.center-align [:img.responsive-img {:style {:height "25em"} :src constants/home-profile-picture}]]
   [:div.col.s12.center-align [:h3 (string/join " " [constants/title constants/full-name])]]
   [:div.col.s12.center-align [:h4 {:style {:margin-top 0}} constants/job-title]]
   [:div.col.s12.center-align [:h5 constants/academic-titles]]
   [:div.col.s12.center-align [:p.flow-text constants/summary]]])


;; about

(defn about-panel []
  [:div {:style {:margin-top "2em"}} (map paragraph constants/about-content)])


;; experience

(defn experience-panel []
  [:div {:style {:margin-top "2em"}} (map paragraph constants/experience-content)])


;; contact

(defn spinner []
  [:div.preloader-wrapper.small.active
   [:div.spinner-layer.spinner-green-only
    [:div.circle-clipper.left
     [:div.circle]]
    [:div.gap-patch
     [:div.circle]]
    [:div.circle-clipper.right
     [:div.circle]]]])

(defn contact-form []
  (let [render-form-field (partial form-field "contact-form-" :contact-form-value-change)
        form-status @(rf/subscribe [:contact-form-status])]
    [:div
     (render-form-field "text" :name "Name")
     (render-form-field "email" :email-address "Email Address")
     (render-form-field "text" :subject "Subject")
     (render-form-field "textarea" :message "Message")
     (cond
       (= form-status :submitting) [spinner]
       (= form-status :submitted) [:p "Your message has been sent" [:i.material-icons.left "done"]]
       :else [:button.btn.waves-effect.waves-light {:on-click #(rf/dispatch [:contact-form-submit])}
               "Submit"
               [:i.material-icons.right "send"]])]))

(defn personal-contact-row [[icon content]]
  [:div.row.personal-contact {:key icon}
   [:div.col.s3 [:i.material-icons.medium icon]]
   [:div.col.s9 (map paragraph content)]])

(defn social-contact [[icon url]]
  [:div.col.s2 {:key icon}
   [:a {:href url :style {:color "black"}}
   [:i.material-icons.medium {:class (str "ion-social-" (name icon))}]]])

(defn contact-methods []
  [:div
    (map personal-contact-row constants/personal-contacts)
    [:div.row
     (map social-contact constants/social-media)]])

(defn contact-panel []
  [:div.row
   [:div.col.s12.m5 {:style {:margin-top "2em"}} (contact-form)]
   [:div.col.s12.m2 {:style {:margin-top "2em"}}]
   [:div.col.s12.m5 {:style {:margin-top "2em"}} (contact-methods)]])


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
  [:div {:class "navbar-fixed"}
   [:nav.blue-grey.lighten-3e
    [:div {:class "nav-wrapper" :style {:text-align "center"}}
     [:ul {:class "center" :style {:display "inline-block"}}
      (map (partial nav-item active-panel) panel-to-details)]]]])


;; footer

(defn footer []
  [:footer.page-footer.blue-grey.lighten-3e
   [:div.footer-copyright
    [:div.container "© 2018 Copyright Text"]]])


;; main

(defn main-panel []
  (let [active-panel (rf/subscribe [:active-panel])]
    [:div
     (nav-panel @active-panel)
     [:div.container [(:component (get panel-to-details @active-panel {:component [:div]}))]]
     [footer]]))
