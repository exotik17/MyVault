# Anteproyecto — Programación Móvil 2

## Nombre del proyecto
**MyVault**

## Objetivo general
Desarrollar una aplicación móvil que permita a los usuarios registrar sus objetos personales indicando su ubicación física de almacenamiento (clósets, cajones, cuartos útiles, etc.), así como la información de compra, factura y garantía asociada, y que además sugiera de forma inteligente qué objetos y zonas de almacenamiento llevan mucho tiempo sin usarse, para apoyar decisiones de desapego (donar, vender o descartar).

## Problema que resuelve
Las personas acumulan objetos en distintos espacios de la casa (no solo "bodegas" formales) y con el tiempo pierden el rastro de qué tienen, dónde lo guardaron, si conservan la factura y si sigue en garantía. Además, rara vez se dan cuenta de cuánto tiempo llevan sin usar ciertos objetos o sin siquiera entrar a ciertas zonas de almacenamiento, lo que lleva a acumulación innecesaria.

## Alcance funcional

### Módulo 1 — Registro de objetos
- Crear/editar/eliminar objetos con foto, nombre, categoría y ubicación física
- Asociar cada objeto a una "zona" de almacenamiento (clóset, cajón, cuarto útil, baúl, etc.)
- Búsqueda y filtros por ubicación, categoría o estado

### Módulo 2 — Garantías y facturas
- Adjuntar foto de la factura a cada objeto
- Registrar fecha de compra y vencimiento de garantía
- Notificaciones locales cuando una garantía está por vencer

### Módulo 3 — Ubicación física (diferenciador técnico)
- Generar códigos QR para cajas/zonas de almacenamiento
- Escanear un QR y ver automáticamente todo su contenido
- Registrar la última vez que se "visitó" o consultó cada zona (manual o vía escaneo de QR)

### Módulo 4 — Sugerencias de desapego (reto principal del proyecto)
- Timestamp de última interacción por **objeto** (registro, búsqueda, marcado como usado)
- Timestamp de última visita por **zona** (última vez que se abrió/consultó esa zona)
- Score combinado de inactividad: tiempo sin tocar el objeto + tiempo sin visitar su zona + categoría del objeto (la ropa de temporada no pesa igual que un electrodoméstico)
- Reporte periódico (ej. mensual) con dos vistas:
  - "Objetos que no usas" — candidatos individuales a donar/vender
  - "Zonas que no visitas" — áreas completas de baja actividad, útiles cuando hay poca data por objeto pero la zona entera lleva tiempo sin revisarse

## Funcionalidades técnicas a evidenciar (para la materia)
| Funcionalidad | Tecnología sugerida |
|---|---|
| Cámara y galería | CameraX / AVFoundation / image_picker |
| OCR de facturas (fecha, monto) | ML Kit Text Recognition / Tesseract |
| Generación y lectura de QR | ZXing / MLKit Barcode |
| Notificaciones locales | WorkManager / Notification API |
| Autenticación de usuario | Firebase Auth |
| Persistencia local | Room / SQLite |
| Sincronización en la nube | Firebase Firestore / REST API propio |
| Arquitectura | MVVM |

## Alcance por prioridad (si el tiempo apremia)
1. CRUD de objetos + foto + ubicación (base del proyecto)
2. Notificaciones de vencimiento de garantía
3. QR para zonas de almacenamiento + registro de última visita
4. Score y reporte de desapego (objeto + zona) — reto técnico central
5. OCR automático de facturas *(opcional / stretch goal)*

## Entregables esperados
- App funcional (APK o build de prueba)
- Backend/base de datos configurada
- Documento de arquitectura
- Manual de usuario breve
