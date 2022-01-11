package party.lemons.biomemakeover.util;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class MerchantTrades
{
    public static class EmeraldForItems
            implements VillagerTrades.ItemListing {
        private final Item item;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EmeraldForItems(ItemLike itemLike, int i, int j, int k) {
            this.item = itemLike.asItem();
            this.cost = i;
            this.maxUses = j;
            this.villagerXp = k;
            this.priceMultiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemStack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class ItemsForEmeralds
            implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeralds(Block block, int i, int j, int k, int l) {
            this(new ItemStack(block), i, j, k, l);
        }

        public ItemsForEmeralds(Item item, int i, int j, int k) {
            this(new ItemStack(item), i, j, 12, k);
        }

        public ItemsForEmeralds(Item item, int i, int j, int k, int l) {
            this(new ItemStack(item), i, j, k, l);
        }

        public ItemsForEmeralds(ItemStack itemStack, int i, int j, int k, int l) {
            this(itemStack, i, j, k, l, 0.05f);
        }

        public ItemsForEmeralds(ItemStack itemStack, int i, int j, int k, int l, float f) {
            this.itemStack = itemStack;
            this.emeraldCost = i;
            this.numberOfItems = j;
            this.maxUses = k;
            this.villagerXp = l;
            this.priceMultiplier = f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class SuspiciousStewForEmerald
            implements VillagerTrades.ItemListing {
        final MobEffect effect;
        final int duration;
        final int xp;
        private final float priceMultiplier;

        public SuspiciousStewForEmerald(MobEffect mobEffect, int i, int j) {
            this.effect = mobEffect;
            this.duration = i;
            this.xp = j;
            this.priceMultiplier = 0.05f;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.saveMobEffect(itemStack, this.effect, this.duration);
            return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemStack, 12, this.xp, this.priceMultiplier);
        }
    }

    public static class ItemsAndEmeraldsToItems
            implements VillagerTrades.ItemListing {
        private final ItemStack fromItem;
        private final int fromCount;
        private final int emeraldCost;
        private final ItemStack toItem;
        private final int toCount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsAndEmeraldsToItems(ItemLike itemLike, int i, Item item, int j, int k, int l) {
            this(itemLike, i, 1, item, j, k, l);
        }

        public ItemsAndEmeraldsToItems(ItemLike itemLike, int i, int j, Item item, int k, int l, int m) {
            this.fromItem = new ItemStack(itemLike);
            this.fromCount = i;
            this.emeraldCost = j;
            this.toItem = new ItemStack(item);
            this.toCount = k;
            this.maxUses = l;
            this.villagerXp = m;
            this.priceMultiplier = 0.05f;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity, Random random) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class EnchantedItemForEmeralds
            implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int baseEmeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EnchantedItemForEmeralds(Item item, int i, int j, int k) {
            this(item, i, j, k, 0.05f);
        }

        public EnchantedItemForEmeralds(Item item, int i, int j, int k, float f) {
            this.itemStack = new ItemStack(item);
            this.baseEmeraldCost = i;
            this.maxUses = j;
            this.villagerXp = k;
            this.priceMultiplier = f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            int i = 5 + random.nextInt(15);
            ItemStack itemStack = EnchantmentHelper.enchantItem(random, new ItemStack(this.itemStack.getItem()), i, false);
            int j = Math.min(this.baseEmeraldCost + i, 64);
            ItemStack itemStack2 = new ItemStack(Items.EMERALD, j);
            return new MerchantOffer(itemStack2, itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class EmeraldsForVillagerTypeItem
            implements VillagerTrades.ItemListing {
        private final Map<VillagerType, Item> trades;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;

        public EmeraldsForVillagerTypeItem(int i, int j, int k, Map<VillagerType, Item> map) {
            Registry.VILLAGER_TYPE.stream().filter(villagerType -> !map.containsKey(villagerType)).findAny().ifPresent(villagerType -> {
                throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getKey((VillagerType)villagerType));
            });
            this.trades = map;
            this.cost = i;
            this.maxUses = j;
            this.villagerXp = k;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity, Random random) {
            if (entity instanceof VillagerDataHolder) {
                ItemStack itemStack = new ItemStack(this.trades.get(((VillagerDataHolder)((Object)entity)).getVillagerData().getType()), this.cost);
                return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, 0.05f);
            }
            return null;
        }
    }

    public static class TippedArrowForItemsAndEmeralds
            implements VillagerTrades.ItemListing {
        private final ItemStack toItem;
        private final int toCount;
        private final int emeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final Item fromItem;
        private final int fromCount;
        private final float priceMultiplier;

        public TippedArrowForItemsAndEmeralds(Item item, int i, Item item2, int j, int k, int l, int m) {
            this.toItem = new ItemStack(item2);
            this.emeraldCost = k;
            this.maxUses = l;
            this.villagerXp = m;
            this.fromItem = item;
            this.fromCount = i;
            this.toCount = j;
            this.priceMultiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemStack = new ItemStack(Items.EMERALD, this.emeraldCost);
            List list = Registry.POTION.stream().filter(potion -> !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion)).collect(Collectors.toList());
            Potion potion2 = (Potion)list.get(random.nextInt(list.size()));
            ItemStack itemStack2 = PotionUtils.setPotion(new ItemStack(this.toItem.getItem(), this.toCount), potion2);
            return new MerchantOffer(itemStack, new ItemStack(this.fromItem, this.fromCount), itemStack2, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class EnchantBookForEmeralds
            implements VillagerTrades.ItemListing {
        private final int villagerXp;

        public EnchantBookForEmeralds(int i) {
            this.villagerXp = i;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            List list = Registry.ENCHANTMENT.stream().filter(Enchantment::isTradeable).collect(Collectors.toList());
            Enchantment enchantment = (Enchantment)list.get(random.nextInt(list.size()));
            int i = Mth.nextInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i));
            int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
            if (enchantment.isTreasureOnly()) {
                j *= 2;
            }
            if (j > 64) {
                j = 64;
            }
            return new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemStack, 12, this.villagerXp, 0.2f);
        }
    }

    public static class TreasureMapForEmeralds
            implements VillagerTrades.ItemListing {
        private final int emeraldCost;
        private final StructureFeature<?> destination;
        private final MapDecoration.Type destinationType;
        private final int maxUses;
        private final int villagerXp;

        public TreasureMapForEmeralds(int i, StructureFeature<?> structureFeature, MapDecoration.Type type, int j, int k) {
            this.emeraldCost = i;
            this.destination = structureFeature;
            this.destinationType = type;
            this.maxUses = j;
            this.villagerXp = k;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity, Random random) {
            if (!(entity.level instanceof ServerLevel)) {
                return null;
            }
            ServerLevel serverLevel = (ServerLevel)entity.level;
            BlockPos blockPos = serverLevel.findNearestMapFeature(this.destination, entity.blockPosition(), 100, true);
            if (blockPos != null) {
                ItemStack itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), (byte)2, true, true);
                MapItem.renderBiomePreviewMap(serverLevel, itemStack);
                MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", this.destinationType);
                itemStack.setHoverName(new TranslatableComponent("filled_map." + this.destination.getFeatureName().toLowerCase(Locale.ROOT)));
                return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(Items.COMPASS), itemStack, this.maxUses, this.villagerXp, 0.2f);
            }
            return null;
        }
    }

    public static class DyedArmorForEmeralds
            implements VillagerTrades.ItemListing {
        private final Item item;
        private final int value;
        private final int maxUses;
        private final int villagerXp;

        public DyedArmorForEmeralds(Item item, int i) {
            this(item, i, 12, 1);
        }

        public DyedArmorForEmeralds(Item item, int i, int j, int k) {
            this.item = item;
            this.value = i;
            this.maxUses = j;
            this.villagerXp = k;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemStack = new ItemStack(Items.EMERALD, this.value);
            ItemStack itemStack2 = new ItemStack(this.item);
            if (this.item instanceof DyeableArmorItem) {
                ArrayList<DyeItem> list = Lists.newArrayList();
                list.add(DyedArmorForEmeralds.getRandomDye(random));
                if (random.nextFloat() > 0.7f) {
                    list.add(DyedArmorForEmeralds.getRandomDye(random));
                }
                if (random.nextFloat() > 0.8f) {
                    list.add(DyedArmorForEmeralds.getRandomDye(random));
                }
                itemStack2 = DyeableLeatherItem.dyeArmor(itemStack2, list);
            }
            return new MerchantOffer(itemStack, itemStack2, this.maxUses, this.villagerXp, 0.2f);
        }

        private static DyeItem getRandomDye(Random random) {
            return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
        }
    }
}
