(ns hello-world.core
  (:require ["express" :as express]
            [promesa.core :as p]))

(def app (express))
(def port 3000)

(defn- send-result [res data]
  (. res send (:body data)))

(def global-promise (p/deferred))

(p/resolve! global-promise
  {:body "Hello, world inside a promise!"})

(defn handle-root-2 [req]
  global-promise)

(defn- register-get [app handler]
  (. app get "/" (fn [req res]
                   (p/let [response (handler req)]
                     (send-result res response)))))

(defn register-handlers []
  (register-get app handle-root-2)
  (. app listen port (fn []
                       (println "Example app listenning on port" port))))

(defn ^:dev/after-load main []
  (def server (register-handlers)))

(defn ^:dev/before-load stop-server []
  (. server close)
  (println "Stopped server"))
