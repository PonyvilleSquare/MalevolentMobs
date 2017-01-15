package com.hepolite.mmob.abilities.passives;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.hepolite.mmob.abilities.PassiveAura;
import com.hepolite.mmob.mobs.MalevolentMob;
import com.hepolite.mmob.settings.Settings;
import com.hepolite.mmob.utility.ParticleEffect;
import com.hepolite.mmob.utility.ParticleEffect.ParticleType;

/**
 * The poison mist applies a poison effect to all entities within range
 */
public class PassivePoisonMist extends PassiveAura {
    private PotionEffect effect = null;

    public PassivePoisonMist(final MalevolentMob mob, final float scale) {
        super(mob, "Poison Mist", scale);
    }

    @Override
    public void loadFromConfig(final Settings settings, final Settings alternative) {
        super.loadFromConfig(settings, alternative);

        final float strength = settings.getScaledValue(alternative, "Strength", scale, 1.0f);
        effect = new PotionEffect(PotionEffectType.POISON, 50, (int) strength - 1);
    }

    @Override
    protected void applyAuraEffect(final LivingEntity entity) {
        entity.addPotionEffect(effect);
    }

    @Override
    protected void displayAura(final Location location, final float range) {
        ParticleEffect.play(ParticleType.CRIT, location, 0.05f, (int) (15.0f * range), 0.5f * range);
    }

}