package party.lemons.biomemakeover.level.generate.foliage;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import party.lemons.biomemakeover.init.BMWorldGen;

import java.util.Random;

public class BalsaSaplingGenerator extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bl) {
        return BMWorldGen.MushroomFields.BLIGHTED_BALSA;
    }
}
