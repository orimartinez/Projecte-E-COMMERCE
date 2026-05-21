# Projecte-E-COMMERCE
# Sistema de Gestió de Botiga de Roba

Aquest projecte consisteix en una aplicació de gestió per a una botiga especialitzada en la venda de pantalons i camises. El sistema permet gestionar l'inventari, els clients i el procés de venda (TPV), incloent-hi la importació de dades i generació d'informes de beneficis.

## Funcionalitats Principals

### 1. Gestió d'Articles
L'aplicació permet el manteniment complet (CRUD) dels articles. Els articles es divideixen en dues famílies:
* **Camises:** Gestió específica de talla de coll (36-52) i amplada de pit (10-15).
* **Pantalons:** Gestió específica de llargada de camal (32-46) i talla de cintura (24-56).
* **Dades comunes:** ID (PK), nom, preu base, IVA (4-21%) i estoc (no negatiu).

### 2. Gestió de Clients
Manteniment de la base de dades de clients amb:
* DNI (PK), Nom, Email i Telèfon.
* Inclou el codi especial **"000"** per a clients genèrics no registrats.

### 3. TPV (Terminal Punt de Venda)
El cor de l'aplicació per registrar vendes:
* Identificació de client (per NIF o codi "000").
* Introducció iterativa d'articles i quantitats.
* **Control d'estoc:** No es permet la venda si no hi ha prou existències.
* **Actualització automàtica:** En confirmar la venda, es descompta l'estoc i es genera el tiquet i les seves línies de factura.
* **Impressió:** Simulació visual del tiquet final amb preus base, IVA i total.

### 4. Importació Automàtica (JSON)
Sistema per carregar dades enviades des de la central:
* Llegeix un fitxer JSON i el carrega en memòria.
* Mostra un resum previ (quants pantalons i camises s'importaran).
* Actualitza articles existents o en crea de nous.
* Informa del total d'articles afegits i actualitzats.

## Consultes i Informes

* **Vendes per Client:** Mostra el NIF, nom, nombre de tiquets i despesa total d'un client específic.
* **Vendes per Article:** Mostra el codi, nom i quantitat total venuda d'un article.
* **Càlcul de Beneficis:** Informe detallat de costos de producció i beneficis:
    * *Cost Pantaló:* `preu_base * 0,30 + llargada_camal * 0,2`
    * *Cost Camisa:* `preu_base * 0,35 + talla_coll * 0,3`
* **Recompra Automàtica:** Genera una proposta de compra quan l'estoc baixa d'un llindar definit per l'usuari, generant un JSON de comanda i actualitzant l'estoc.

## Estructura del Menú

1.  **Importació articles:** Càrrega des de JSON.
2.  **Gestió d'articles:** Submenú d'altes, baixes, modificacions i consultes.
3.  **Gestió de clients:** Submenú d'altes, baixes, modificacions i consultes.
4.  **TPV:** Registre de vendes i actualització d'estoc.
5.  **Consultes vendes per client:** Històric de despesa.
6.  **Consultes vendes per article:** Volum de vendes per producte.
7.  **Calcula beneficis totals:** Informe de rendibilitat.
8.  **Recompra automàtica articles:** Gestió d'estoc mínim i comandes.

## Requisits Tècnics (Model de Dades)

### Tiquets i Línies
Cada venda es registra amb un **Tiquet** (ID, data, DNI, totals) que conté múltiples **Línies de Factura** (ID tiquet, ID article, quantitat, preus unitaris i totals).