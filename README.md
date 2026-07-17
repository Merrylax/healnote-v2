# 🎮 HealNote V2

**Un plugin Minecraft avec un système de maudits et de sauveurs !**

## 📋 Fonctionnalités

### ☠️ Death Note
Ce livre maudit condamne un joueur :
- La nuit tombe immédiatement
- Un orage éclate avec des éclairs
- Tous les joueurs sont notifiés
- **Après 120 secondes** : Le joueur meurt et est banni définitivement
- **Les Totems d'immortalité sont IGNORÉS**
- Effets visuels (Cécité, Nausée)
- Dégâts progressifs dans les 30 dernières secondes

### 💚 Heal Note
Sauvez un joueur condamné :
- Le joueur condamné est sauvé
- **MAIS LE SAUVEUR meurt à sa place**
- **ET LE SAUVEUR EST BANNI DÉFINITIVEMENT**
- La cible ne subit aucune pénalité (sauvée)

### 📖 Deban Book
Invoque le **Gardien du Destin** :
- Lance un défi de **45 minutes** pour tout le serveur
- Si le Gardien est vaincu : Le joueur est débanni automatiquement
- Si le Gardien n'est pas vaincu : Le joueur reste banni
- Le livre est consommé à l'utilisation

### 👹 Gardien du Destin
**Un boss ultime :**
- **6× les PV d'un Warden** (384 HP)
- **4× les dégâts d'un Warden** (32 dégâts/coup)
- Plus rapide et plus résistant
- BossBar visible par tous
- Nom personnalisé et visible

## 🔧 Recettes

### 📜 Page Maudite
```
NND
NDN
DND

N = Nether Star
D = Deepslate Renforcée (minable)
```

### ☠️ Death Note
```
PPP
PEP
PPP

P = Papier
E = Table d'Enchantement
```

### 💚 Heal Note
```
GGG
GEG
GGG

G = Bloc d'Or
E = Table d'Enchantement
```

### 📖 Deban Book
```
DDD
DED
DDD

D = Bloc de Diamant
E = Table d'Enchantement
```

## 📦 Installation

1. Téléchargez le `.jar` depuis les **Releases**
2. Mettez-le dans le dossier `plugins/` de votre serveur
3. Redémarrez le serveur
4. C'est bon !

## 🎯 Commandes

- `/healnote` - Commande principale
- `/deathban <joueur>` - Bannir avec le Death Note
- `/deban <joueur>` - Débannir avec le Deban Book

## 📋 Permissions

- `healnote.use` - Utiliser Death Note & Heal Note
- `healnote.immune` - Immunisé contre Death Note
- `healnote.deban` - Utiliser le Deban Book
- `healnote.admin` - Admin complet

## 👨‍💻 Développeur

**Merrylax**

---

**Version : 2.0.0**
