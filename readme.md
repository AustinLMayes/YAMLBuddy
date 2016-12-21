###LifeBoatYAMLBuddy###

This plugin is a tool that will generate LB-ready YAML from regions and inventories.

####Requirements####
- _WorldEdit_

####Commands####
`/yamlbuddy`&nbsp;&nbsp; - Parent command, all commands should be prefixed with this.   
&nbsp;&nbsp;`inventory`&nbsp;&nbsp;- Inventory parent command. All functions dealing with inventories are prefixed with this.   
&nbsp;&nbsp;&nbsp;&nbsp;`all`&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Generate a loadout from your inventory.    
&nbsp;&nbsp;&nbsp;&nbsp;`hotbar`&nbsp;&nbsp; - Generate a loadout from your hotbar.    
&nbsp;&nbsp;&nbsp;&nbsp;`armor`&nbsp;&nbsp;&nbsp;&nbsp;- Generate a loadout from the armor you are wearing.    
&nbsp;&nbsp;&nbsp;&nbsp;`potion`&nbsp;&nbsp; - Generate a YAML list from the effects in the potion you are holding.     
   
&nbsp;&nbsp;__NOTE:__ All regons are session-based and saved in your personal region cache.    
&nbsp;&nbsp;`region`&nbsp;&nbsp;&nbsp;&nbsp; - Region parent command, all commands involving regions should be prefixed with this.     
&nbsp;&nbsp;&nbsp;&nbsp;`cuboid`&nbsp;&nbsp; - Generate a cuboid based on the current WorldEdit selection.   
&nbsp;&nbsp;&nbsp;&nbsp;`cylinder` - Generate a cylinder based on your current position.   
&nbsp;&nbsp;&nbsp;&nbsp;`sphere`&nbsp;&nbsp; - Generate a sphere based on your current position.   
&nbsp;&nbsp;&nbsp;&nbsp;`point`&nbsp;&nbsp;&nbsp;&nbsp;- Generate a point based on where you are looking.    
&nbsp;&nbsp;&nbsp;&nbsp;`upload`&nbsp;&nbsp; - Upload all generated regions to DPaste.   
