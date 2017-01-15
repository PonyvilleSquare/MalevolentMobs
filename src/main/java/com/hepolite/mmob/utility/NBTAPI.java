package com.hepolite.mmob.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.hepolite.mmob.Log;
import com.hepolite.mmob.settings.Settings;

@SuppressWarnings("rawtypes")
public class NBTAPI {
    // Resources that are needed

    private static Class classCraftItemStack;

    private static Class classNMSItemStack;

    private static Class classNBTBase;

    private static Class classNBTTagCompound;

    private static Class classNBTTagList;

    private static Class classNBTTagString;

    private static Class classNBTTagInt;

    private static Class classNBTTagLong;

    private static Class classNBTTagShort;

    private static Class classNBTTagByte;

    private static Class classNBTTagFloat;

    private static Class classNBTTagDouble;

    private static Field CraftItemStack_handle;

    private static Method CraftItemStack_asCraftCopy;
    private static Method ItemStack_setTag;
    private static Method ItemStack_getTag;
    private static Method ItemStack_hasTag;
    private static Method NBTTagCompound_setString, NBTTagCompound_getString;
    private static Method NBTTagCompound_setInt, NBTTagCompound_getInt;
    private static Method NBTTagCompound_setLong, NBTTagCompound_getLong;
    private static Method NBTTagCompound_setShort, NBTTagCompound_getShort;
    private static Method NBTTagCompound_setByte, NBTTagCompound_getByte;
    private static Method NBTTagCompound_setFloat, NBTTagCompound_getFloat;
    private static Method NBTTagCompound_setDouble, NBTTagCompound_getDouble;
    private static Method NBTTagCompound_setTag, NBTTagCompound_getCompound, NBTTagCompound_getTag;
    private static Method NBTTagCompound_remove;
    private static Method NBTTagCompound_hasKey, NBTTagCompound_getKeys;
    private static Method NBTTagList_add, NBTTagList_remove, NBTTagList_get, NBTTagList_size;
    private static Method NBTTagString_get;
    private static Method NBTTagInt_get, NBTTagLong_get, NBTTagShort_get, NBTTagByte_get;
    private static Method NBTTagFloat_get, NBTTagDouble_get;

    private static Settings mappings;

    /** Initialize the API */
    @SuppressWarnings("unchecked")
    public final static void initialize() {
        try {
            mappings = new Settings("mappings.yml");

            // Grab various bits of data from the server
            final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            // Grab classes
            classCraftItemStack = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            classNMSItemStack = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.ItemStack"));

            classNBTBase = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTBase"));
            classNBTTagCompound = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagCompound"));
            classNBTTagList = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagList"));
            classNBTTagString = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagString"));
            classNBTTagInt = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagInt"));
            classNBTTagLong = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagLong"));
            classNBTTagShort = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagShort"));
            classNBTTagByte = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagByte"));
            classNBTTagFloat = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagFloat"));
            classNBTTagDouble = Class.forName("net.minecraft.server." + version + "." + mappings.getString("class.NBTTagDouble"));

            // Grab fields
            CraftItemStack_handle = classCraftItemStack.getDeclaredField("handle");
            CraftItemStack_handle.setAccessible(true);

            // Grab methods
            ItemStack_setTag = classNMSItemStack.getMethod(mappings.getString("method.ItemStack.setTag"), classNBTTagCompound);
            ItemStack_getTag = classNMSItemStack.getMethod(mappings.getString("method.ItemStack.getTag"));
            ItemStack_hasTag = classNMSItemStack.getMethod(mappings.getString("method.ItemStack.hasTag"));

            CraftItemStack_asCraftCopy = classCraftItemStack.getMethod("asCraftCopy", ItemStack.class);

            NBTTagCompound_setString = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setString"), String.class, String.class);
            NBTTagCompound_getString = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getString"), String.class);
            NBTTagCompound_setInt = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setInt"), String.class, int.class);
            NBTTagCompound_getInt = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getInt"), String.class);
            NBTTagCompound_setLong = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setLong"), String.class, long.class);
            NBTTagCompound_getLong = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getLong"), String.class);
            NBTTagCompound_setByte = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setByte"), String.class, byte.class);
            NBTTagCompound_getByte = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getByte"), String.class);
            NBTTagCompound_setShort = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setShort"), String.class, short.class);
            NBTTagCompound_getShort = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getShort"), String.class);
            NBTTagCompound_setFloat = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setFloat"), String.class, float.class);
            NBTTagCompound_getFloat = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getFloat"), String.class);
            NBTTagCompound_setDouble = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setDouble"), String.class, double.class);
            NBTTagCompound_getDouble = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getDouble"), String.class);
            NBTTagCompound_setTag = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.setTag"), String.class, classNBTBase);
            NBTTagCompound_getCompound = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getCompound"), String.class);
            NBTTagCompound_getTag = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getTag"), String.class);
            NBTTagCompound_remove = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.remove"), String.class);
            NBTTagCompound_hasKey = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.hasKey"), String.class);
            NBTTagCompound_getKeys = classNBTTagCompound.getMethod(mappings.getString("method.NBTTagCompound.getKeys"));
            NBTTagList_add = classNBTTagList.getMethod(mappings.getString("method.NBTTagList.add"), classNBTBase);
            NBTTagList_remove = classNBTTagList.getMethod(mappings.getString("method.NBTTagList.remove"), int.class);
            NBTTagList_get = classNBTTagList.getMethod(mappings.getString("method.NBTTagList.get"), int.class);
            NBTTagList_size = classNBTTagList.getMethod(mappings.getString("method.NBTTagList.size"));
            NBTTagString_get = classNBTTagString.getMethod(mappings.getString("method.NBTTagString.get"));
            NBTTagInt_get = classNBTTagInt.getMethod(mappings.getString("method.NBTTagInt.get"));
            NBTTagLong_get = classNBTTagLong.getMethod(mappings.getString("method.NBTTagLong.get"));
            NBTTagShort_get = classNBTTagShort.getMethod(mappings.getString("method.NBTTagShort.get"));
            NBTTagByte_get = classNBTTagByte.getMethod(mappings.getString("method.NBTTagByte.get"));
            NBTTagFloat_get = classNBTTagFloat.getMethod(mappings.getString("method.NBTTagFloat.get"));
            NBTTagDouble_get = classNBTTagDouble.getMethod(mappings.getString("method.NBTTagDouble.get"));

            // Finalize
            Log.log("No problems were detected in the NBT API!");
        } catch (final Exception e) {
            Log.log("Failed to initialize the NBT Api! Is the plugin outdated?", Level.WARNING);
            e.printStackTrace();
        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////

    /** Creates a new CraftItemStack. Returns a regular ItemStack if the creation failed */
    private static Object createCraftItemStack(final Material material, final int amount, final short damage) {
        try {
            return CraftItemStack_asCraftCopy.invoke(classCraftItemStack, new ItemStack(material, amount, damage));
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to create CraftItemStack", Level.WARNING);
            e.printStackTrace();
        }
        return new ItemStack(material, amount, damage);
    }

    /** Returns a NMS ItemStack from the given ItemStack. Returns null if this failed */
    private static Object getNMSItemStack(final ItemStack itemStack) {
        try {
            return CraftItemStack_handle.get(itemStack);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to obtain NMS ItemStack (Verify that your ItemStack is a CraftItemStack)", Level.WARNING);
        }
        return null;
    }

    /** Creates a new NBTTag of the given type. Returns null if the creation failed */

    private static Object createNBTTag(final Class tagClass) {
        try {
            return tagClass.newInstance();
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to create NBTTag", Level.WARNING);
        }
        return null;
    }

    /** Creates a new NBTTag of the given type, with a parameter. Returns null if the creation failed */
    @SuppressWarnings({ "unchecked" })
    private static Object createNBTTag(final Class tagClass, final Object value) {
        try {
            return tagClass.getConstructor(value.getClass()).newInstance(value);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to create NBTTag with value " + value, Level.WARNING);
        }
        return null;
    }

    /** Assigns the NBTTagCompound to the given ItemStack */
    private static void setNBTTagCompound(final ItemStack itemStack, final Object nbtTagCompound) {
        try {
            final Object nmsItemStack = getNMSItemStack(itemStack);
            ItemStack_setTag.invoke(nmsItemStack, nbtTagCompound);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to assign NBTTagCompound to NMS ItemStack", Level.WARNING);
        }
    }

    /** Returns the NBTTagCompound associated with the given ItemStack */
    private static Object getNBTTagCompound(final ItemStack itemStack) {
        try {
            return ItemStack_getTag.invoke(getNMSItemStack(itemStack));
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to obtain NBTTagCompound", Level.WARNING);
        }
        return null;
    }

    /** Checks if the given ItemStack contains an NBTTagCompound */
    private static boolean hasNBTTagCompound(final ItemStack itemStack) {
        try {
            return (boolean) ItemStack_hasTag.invoke(getNMSItemStack(itemStack));
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to check if NBTTagCompound is on NMS ItemStack", Level.WARNING);
        }
        return false;
    }

    /** Attempts to store a value in the NBTTagCompound, using the given key and storing method */
    private static void setNBTTagCompoundValue(final Object nbtTagCompound, final Method method, final String key, final Object value) {
        try {
            method.invoke(nbtTagCompound, key, value);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to assign value to NBTTagCompound", Level.WARNING);
        }
    }

    /** Attempts to retrieve a value in the NBTTagCompound, using the given key and storing method */
    private static Object getNBTTagCompoundValue(final Object nbtTagCompound, final Method method, final String key) {
        try {
            return method.invoke(nbtTagCompound, key);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to obtain NBTTagCompound value", Level.WARNING);
        }
        return null;
    }

    /** Attempts to check if a certain key exists in the NBTTagCompound */
    private static boolean hasNBTTagCompoundKey(final Object nbtTagCompound, final String key) {
        try {
            return (boolean) NBTTagCompound_hasKey.invoke(nbtTagCompound, key);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to check if NBTTagCompound has key", Level.WARNING);
        }
        return false;
    }

    /** Attempts to remove a certain key exists in the NBTTagCompound */
    private static void removeNBTTagCompoundKey(final Object nbtTagCompound, final String key) {
        try {
            NBTTagCompound_remove.invoke(nbtTagCompound, key);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to remove NBTTagCompound key", Level.WARNING);
        }
    }

    /** Attempts to retrieve all keys that exists in the NBTTagCompound */
    @SuppressWarnings("unchecked")
    private static Set<String> getAllNBTTagCompoundKeys(final Object nbtTagCompound) {
        try {
            return (Set<String>) NBTTagCompound_getKeys.invoke(nbtTagCompound);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to obtain all NBTTagCompound keys", Level.WARNING);
        }
        return null;
    }

    /** Attempts to insert a new object into the NBTTagList */

    private static void addNBTTagListEntry(final Object nbtTagList, final Class tagClass, final Object value) {
        try {
            if (tagClass == classNBTTagCompound || tagClass == classNBTTagList)
                NBTTagList_add.invoke(nbtTagList, value);
            else
                NBTTagList_add.invoke(nbtTagList, createNBTTag(tagClass, value));
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to add value to NBTTagList", Level.WARNING);
        }
    }

    /** Attempts to remove an object from the NBTTagList. Will return the object that was removed */
    private static Object removeNBTTagListEntry(final Object nbtTagList, final int index) {
        try {
            return NBTTagList_remove.invoke(nbtTagList, index);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to remove value from NBTTagList", Level.WARNING);
        }
        return null;
    }

    /** Attempts to retrieve an NBTBase from the NBTTagList */
    private static Object getNBTTagListEntry(final Object nbtTagList, final int index) {
        try {
            return NBTTagList_get.invoke(nbtTagList, index);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to obtain NBTTagList value", Level.WARNING);
        }
        return null;
    }

    /** Attempts to retrieve the size of the given NBTTagList */
    private static int sizeNBTTagList(final Object nbtTagList) {
        try {
            return (int) NBTTagList_size.invoke(nbtTagList);
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to obtain NBTTagList size", Level.WARNING);
        }
        return 0;
    }

    /** Attempts to convert from the given NBTTag[Type] to a standard type */
    @SuppressWarnings({ "unchecked" })
    private static Object convert(final Object nbtTag) {
        try {
            final Class nbtClass = nbtTag.getClass();
            if (nbtClass.isAssignableFrom(classNBTTagString))
                return NBTTagString_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagInt))
                return NBTTagInt_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagLong))
                return NBTTagLong_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagShort))
                return NBTTagShort_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagByte))
                return NBTTagByte_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagFloat))
                return NBTTagFloat_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagDouble))
                return NBTTagDouble_get.invoke(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagCompound))
                return new NBTTag(nbtTag);
            else if (nbtClass.isAssignableFrom(classNBTTagList))
                return new NBTList(nbtTag);
            else
                Log.log("[NBTAPI] Failed to convert '" + nbtTag + "'!");
        } catch (final Exception e) {
            Log.log("[NBTAPI] Failed to convert NMS class", Level.WARNING);
        }
        return null;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////

    /** Returns a new ItemStack that is compatible with the NBTAPI system right off the bat; the returned ItemStack will be a CraftItemStack, or a regular ItemStack if something went wrong */
    public final static ItemStack getItemStack(final Material material, final int amount, final int meta) {
        return (ItemStack) createCraftItemStack(material, amount, (short) meta);
    }

    /** Assigns a NBTTag to the given ItemStack. Returns the same item that was passed in */
    public final static ItemStack setTag(final ItemStack itemStack, final NBTTag nbtTag) {
        setNBTTagCompound(itemStack, nbtTag.nmsTag);
        return itemStack;
    }

    /** Returns a new tag from the given ItemStack, or null if there was no tag associated with the item */
    public final static NBTTag getTag(final ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return null;
        final Object nmsTag = getNBTTagCompound(itemStack);
        if (nmsTag == null)
            return null;
        return new NBTTag(nmsTag);
    }

    /** Returns true if the given ItemStack has a NBTTag associated with it */
    public final static boolean hasTag(final ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return false;
        return hasNBTTagCompound(itemStack);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////

    /** Simple wrapper for the programmer; uses a NBTTag to communicate with the NBTTagCompound */
    public final static class NBTTag {
        // Control variables
        private final Object nmsTag;

        private final HashMap<String, String> types = new HashMap<String, String>();

        /** Initializes a new, empty, tag object */
        public NBTTag() {
            nmsTag = createNBTTag(classNBTTagCompound);
        }

        /** Initializes a new tag object which contains the given tag */
        private NBTTag(final Object nmsTag) {
            this.nmsTag = nmsTag;

            final Set<String> keys = getAllNBTTagCompoundKeys(nmsTag);
            for (final String key : keys) {
                final Object value = convert(getNBTTagCompoundValue(nmsTag, NBTTagCompound_getTag, key));
                if (value instanceof String)
                    types.put(key, "string");
                else if (value instanceof Integer)
                    types.put(key, "int");
                else if (value instanceof Long)
                    types.put(key, "long");
                else if (value instanceof Short)
                    types.put(key, "short");
                else if (value instanceof Byte)
                    types.put(key, "byte");
                else if (value instanceof Float)
                    types.put(key, "float");
                else if (value instanceof Double)
                    types.put(key, "double");
                else if (value instanceof NBTTag)
                    types.put(key, "tag");
                else if (value instanceof NBTList)
                    types.put(key, "list");
                else
                    Log.log("[NBTAPI NBTTag] Was unable to decode '" + value + "'!");
            }
        }

        // //////////////////////////////////////////////////////

        /** Returns the size of the tag (number of keys) */
        public int size() {
            return types.size();
        }

        /** Checks if the NBT tag has the given key */
        public boolean hasKey(final String key) {
            return hasNBTTagCompoundKey(nmsTag, key);
        }

        /** Removes the given key from the NBT tag */
        public void remove(final String key) {
            types.remove(key);
            removeNBTTagCompoundKey(nmsTag, key);
        }

        /** Returns all the keys used within this tag */
        public Set<String> getKeys() {
            return getAllNBTTagCompoundKeys(nmsTag);
        }

        /** Returns the type of the data stored in the tag */
        public String format(final String key) {
            return types.get(key);
        }

        // //////////////////////////////////////////////////////

        /** Stores a string within the NBT tag */
        public void setString(final String key, final String value) {
            types.put(key, "string");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setString, key, value);
        }

        /** Retrieves a string from the NBT tag */
        public String getString(final String key) {
            if (!types.get(key).equals("string"))
                return "";
            return (String) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getString, key);
        }

        /** Stores an integer within the NBT tag */
        public void setInt(final String key, final int value) {
            types.put(key, "int");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setInt, key, value);
        }

        /** Retrieves an integer from the NBT tag */
        public int getInt(final String key) {
            if (!types.get(key).equals("int"))
                return 0;
            return (int) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getInt, key);
        }

        /** Stores a long within the NBT tag */
        public void setLong(final String key, final long value) {
            types.put(key, "long");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setLong, key, value);
        }

        /** Retrieves a long from the NBT tag */
        public long getLong(final String key) {
            if (!types.get(key).equals("long"))
                return 0L;
            return (long) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getLong, key);
        }

        /** Stores a short within the NBT tag */
        public void setShort(final String key, final short value) {
            types.put(key, "short");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setShort, key, value);
        }

        /** Retrieves a short from the NBT tag */
        public short getShort(final String key) {
            if (!types.get(key).equals("short"))
                return 0;
            return (short) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getShort, key);
        }

        /** Stores a byte within the NBT tag */
        public void setByte(final String key, final byte value) {
            types.put(key, "byte");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setByte, key, value);
        }

        /** Retrieves a byte from the NBT tag */
        public byte getByte(final String key) {
            if (!types.get(key).equals("byte"))
                return 0;
            return (byte) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getByte, key);
        }

        /** Stores a boolean within the NBT tag */
        public void setBoolean(final String key, final boolean value) {
            setByte(key, (byte) (value ? 1 : 0));
        }

        /** Retrieves a boolean from the NBT tag */
        public boolean getBoolean(final String key) {
            return getByte(key) != 0;
        }

        /** Stores a floating point value within the NBT tag */
        public void setFloat(final String key, final float value) {
            types.put(key, "float");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setFloat, key, value);
        }

        /** Retrieves a floating point value from the NBT tag */
        public float getFloat(final String key) {
            if (!types.get(key).equals("float"))
                return 0.0f;
            return (float) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getFloat, key);
        }

        /** Stores a double within the NBT tag */
        public void setDouble(final String key, final double value) {
            types.put(key, "double");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setDouble, key, value);
        }

        /** Retrieves a double from the NBT tag */
        public double getDouble(final String key) {
            if (!types.get(key).equals("double"))
                return 0.0;
            return (double) getNBTTagCompoundValue(nmsTag, NBTTagCompound_getDouble, key);
        }

        /** Stores a NBTTag within the NBT tag */
        public void setTag(final String key, final NBTTag value) {
            types.put(key, "tag");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setTag, key, value.nmsTag);
        }

        /** Retrieves a NBTTag from the NBT tag */
        public NBTTag getTag(final String key) {
            if (!types.get(key).equals("tag"))
                return null;
            return (NBTTag) convert(getNBTTagCompoundValue(nmsTag, NBTTagCompound_getCompound, key));
        }

        /** Stores a NBTList within the NBT tag */
        public void setList(final String key, final NBTList value) {
            types.put(key, "list");
            setNBTTagCompoundValue(nmsTag, NBTTagCompound_setTag, key, value.nmsTag);
        }

        /** Retrieves a NBTList from the NBT tag */
        public NBTList getList(final String key) {
            if (!types.get(key).equals("list"))
                return null;
            return (NBTList) convert(getNBTTagCompoundValue(nmsTag, NBTTagCompound_getTag, key));
        }

        // //////////////////////////////////////////////////////

        @Override
        public String toString() {
            return nmsTag.toString();
        }
    }

    /** Simple wrapper for the programmer; uses a NBTList to communicate with the NBTTagList */
    public final static class NBTList {
        // Control variables
        private final Object nmsTag;

        private final List<String> format = new ArrayList<String>();

        /** Initializes a new, empty, tag object */
        public NBTList() {
            nmsTag = createNBTTag(classNBTTagList);
        }

        /** Initializes a new tag object which contains the given tag */
        private NBTList(final Object nmsTag) {
            this.nmsTag = nmsTag;

            final int size = sizeNBTTagList(nmsTag);
            for (int i = 0; i < size; i++) {
                final Object object = convert(getNBTTagListEntry(nmsTag, i));
                if (object instanceof String)
                    format.add("string");
                else if (object instanceof Integer)
                    format.add("int");
                else if (object instanceof Long)
                    format.add("long");
                else if (object instanceof Short)
                    format.add("short");
                else if (object instanceof Byte)
                    format.add("byte");
                else if (object instanceof Float)
                    format.add("float");
                else if (object instanceof Double)
                    format.add("double");
                else if (object instanceof NBTTag)
                    format.add("tag");
                else if (object instanceof NBTList)
                    format.add("list");
                else {
                    format.add("UNKNOWN");
                    Log.log("[NBTAPI NBTList] Was unable to decode '" + object.getClass() + "'!");
                }
            }
        }

        // //////////////////////////////////////////////////////

        /** Returns the size of the list */
        public int size() {
            return format.size();
        }

        /** Removes an element from the list; returns the object that was removed */
        public Object remove(final int index) {
            if (index < 0 || index >= size())
                throw new ArrayIndexOutOfBoundsException("Attempted to access index " + index + ", with at most " + size() + " indices!");
            format.remove(index);
            return convert(removeNBTTagListEntry(nmsTag, index));
        }

        /** Returns the type of the data stored in the list */
        public String format(final int index) {
            return format.get(index);
        }

        // //////////////////////////////////////////////////////

        /** Adds an element to the list */
        public void addTag(final NBTTag value) {
            format.add("tag");
            addNBTTagListEntry(nmsTag, classNBTTagCompound, value.nmsTag);
        }

        /** Retrieves a tag from the list */
        public NBTTag getTag(final int index) {
            if (!format.get(index).equals("tag"))
                return null;
            return (NBTTag) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a list to the list */
        public void addList(final NBTList value) {
            format.add("list");
            addNBTTagListEntry(nmsTag, classNBTTagList, value.nmsTag);
        }

        /** Retrieves a list from the list */
        public NBTList getList(final int index) {
            if (!format.get(index).equals("list"))
                return null;
            return (NBTList) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a string to the list */
        public void addString(final String value) {
            format.add("string");
            addNBTTagListEntry(nmsTag, classNBTTagString, value);
        }

        /** Retrieves a string from the list */
        public String getString(final int index) {
            if (!format.get(index).equals("string"))
                return "";
            return (String) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds an integer to the list */
        public void addInt(final int value) {
            format.add("int");
            addNBTTagListEntry(nmsTag, classNBTTagInt, value);
        }

        /** Retrieves an integer from the list */
        public int getInt(final int index) {
            if (!format.get(index).equals("int"))
                return 0;
            return (int) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a long to the list */
        public void addLong(final long value) {
            format.add("long");
            addNBTTagListEntry(nmsTag, classNBTTagLong, value);
        }

        /** Retrieves a long from the list */
        public long getLong(final int index) {
            if (!format.get(index).equals("long"))
                return 0L;
            return (long) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a short to the list */
        public void addShort(final short value) {
            format.add("short");
            addNBTTagListEntry(nmsTag, classNBTTagShort, value);
        }

        /** Retrieves a short from the list */
        public short getShort(final int index) {
            if (!format.get(index).equals("short"))
                return 0;
            return (short) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a byte to the list */
        public void addByte(final byte value) {
            format.add("byte");
            addNBTTagListEntry(nmsTag, classNBTTagByte, value);
        }

        /** Retrieves a byte from the list */
        public byte getByte(final int index) {
            if (!format.get(index).equals("byte"))
                return 0;
            return (byte) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a boolean to the list */
        public void addBoolean(final boolean value) {
            addByte((byte) (value ? 1 : 0));
        }

        /** Retrieves a boolean from the list */
        public boolean getBoolean(final int index) {
            return getByte(index) != 0;
        }

        /** Adds a float to the list */
        public void addFloat(final float value) {
            format.add("float");
            addNBTTagListEntry(nmsTag, classNBTTagFloat, value);
        }

        /** Retrieves a float from the list */
        public float getFloat(final int index) {
            if (!format.get(index).equals("float"))
                return 0.0f;
            return (float) convert(getNBTTagListEntry(nmsTag, index));
        }

        /** Adds a double to the list */
        public void addDouble(final double value) {
            format.add("double");
            addNBTTagListEntry(nmsTag, classNBTTagDouble, value);
        }

        /** Retrieves a double from the list */
        public double getDouble(final int index) {
            if (!format.get(index).equals("double"))
                return 0.0;
            return (double) convert(getNBTTagListEntry(nmsTag, index));
        }

        // //////////////////////////////////////////////

        @Override
        public String toString() {
            return nmsTag.toString();
        }
    }
}