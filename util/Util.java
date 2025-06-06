package wolforce.hearthwell.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {

	public static ResourceLocation res(String domainAndPath) {
		return new ResourceLocation(domainAndPath);
	}

	public static ResourceLocation res(String modid, String path) {
		return new ResourceLocation(modid, path);
	}

	//
	//
	//
	// STACKS

	public static boolean isValid(ItemStack stack) {
		return stack != null && stack.getCount() > 0 && !stack.isEmpty() && !stack.getItem().equals(Items.AIR);
	}

	public static boolean equalExceptAmount(ItemStack stack1, ItemStack stack2) {
		if (!Util.isValid(stack1) && !Util.isValid(stack2))
			return true;
		if (!Util.isValid(stack1) && Util.isValid(stack2))
			return false;
		if (Util.isValid(stack1) && !Util.isValid(stack2))
			return false;
		return stack1.getItem() == stack2.getItem() && stack1.getDamageValue() == stack2.getDamageValue() && ( //
		/*    */(!stack1.hasTag() && !stack2.hasTag()) || //
				(stack1.getTag().equals(stack2.getTag())) //
		);
	}

	public static ItemStack stack(Object object) {
		if (object instanceof ItemStack)
			return (ItemStack) object;
		if (object instanceof Block)
			return new ItemStack((Block) object);
		if (object instanceof Item)
			return new ItemStack((Item) object);
		throw new InvalidParameterException(
				"Object of type" + object.getClass() + " cannot be made into an ItemStack.");
	}

	public static FluidStack fluidStack(Fluid f) {
		return new FluidStack(f, FluidAttributes.BUCKET_VOLUME);
	}

	// ITEMS AND BLOCKS

	public static Set<BlockState> getAllStates(Block blockIn) {
		return ImmutableSet.copyOf(blockIn.getStateDefinition().getPossibleStates());
	}

	//
	//
	//
	// LISTS

	public static TextComponent[] stringsToComponents(String[] value) {
		return (TextComponent[]) Arrays.stream(value).map(x -> new TextComponent(x)).toArray();
	}

	@SafeVarargs
	public static <T> List<T> listOf(T... objs) {
		LinkedList<T> list = new LinkedList<T>();
		for (T t : objs)
			list.add(t);
		return list;
	}

	@SafeVarargs
	public static <E> NonNullList<E> nnl(E... items) {
		NonNullList<E> list = NonNullList.create();
		for (E item : items) {
			list.add(item);
		}
		return list;
	}

	@SafeVarargs
	public static <T> T[] array(T... items) {
		return items;
	}

	public static ItemStack[] stackArray(Object... items) {
		ItemStack[] arr = new ItemStack[items.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = stack(items[i]);
		}
		return arr;
	}

	public static <T> T getRandomFromArray(T[] arr) {
		return arr[(int) (Math.random() * arr.length)];
	}

	public static boolean listContains(List<ItemStack> list, Item item) {
		for (ItemStack stack : list) {
			if (stack.getItem() == item)
				return true;
		}
		return false;
	}

	//
	//
	//
	// WORLD

	public static void spawnItem(Level world, Vec3i pos, ItemStack stack) {
		spawnItem(world, new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), stack, 10);
	}

	public static void spawnItem(Level world, Vec3 pos, ItemStack stack) {
		spawnItem(world, pos, stack, 10);
	}

	/**
	 * default pickupDelay = 10 player throw pickupDelay = 40
	 */
	public static void spawnItem(Level world, Vec3 pos, ItemStack stack, int pickupDelay, double... speeds) {
		if (!Util.isValid(stack))
			return;
		ItemEntity entityitem = new ItemEntity(world, pos.x(), pos.y(), pos.z(), stack);
		if (speeds.length == 0) {
			entityitem.setDeltaMovement(new Vec3(//
					Math.random() * .4 - .2, //
					Math.random() * .2, //
					Math.random() * .4 - .2 //
			));
		} else {
			entityitem.setDeltaMovement(new Vec3(//
					speeds[0], //
					speeds[1], //
					speeds[2] //
			));
		}
		entityitem.setPickUpDelay(pickupDelay);
		world.addFreshEntity(entityitem);
	}

	public static <T> T getObjectFromField(Class<?> class1, String fieldName, Object obj) {
		Field field = getField(class1, fieldName);
		field.setAccessible(true);
		return getObjectFromField(field, obj);
	}

	//
	//
	//
	// OTHER

	public static ItemLike randomDye() {
		switch ((int) (Math.random() * 16)) {
		case 0:
			return Items.BLACK_DYE;
		case 1:
			return Items.BLUE_DYE;
		case 2:
			return Items.BROWN_DYE;
		case 3:
			return Items.CYAN_DYE;
		case 4:
			return Items.GRAY_DYE;
		case 5:
			return Items.GREEN_DYE;
		case 6:
			return Items.LIGHT_BLUE_DYE;
		case 7:
			return Items.LIGHT_GRAY_DYE;
		case 8:
			return Items.LIME_DYE;
		case 9:
			return Items.MAGENTA_DYE;
		case 10:
			return Items.ORANGE_DYE;
		case 11:
			return Items.PINK_DYE;
		case 12:
			return Items.PURPLE_DYE;
		case 13:
			return Items.RED_DYE;
		case 14:
			return Items.WHITE_DYE;
		case 15:
			return Items.YELLOW_DYE;
		}
		return null;
	}

	public static boolean blockIsNearBlock(Level world, BlockPos pos, Block block) {
		for (Direction dir : Direction.values())
			if (world.getBlockState(pos.relative(dir)).getBlock() == block)
				return true;
		return false;
	}

	//
	//
	//
	// REFLECTION

	private static Field getField(Class<?> class1, String fieldName) {
		try {
			return class1.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("could not get field " + fieldName + " from class " + class1.getName());
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T getObjectFromField(Field field, Object obj) {
		try {
			return (T) field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("could not get object " + obj + " from field " + field.getName());
		}
	}

	@Nullable
	public static Item tryGetItem(String item) {
		try {
			return ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public static Item getItem(Block block) {
		return Item.byBlock(block);
	}

	@Nullable
	public static Block tryGetBlock(String block) {
		try {
			return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block));
		} catch (Exception e) {
			return null;
		}
	}

	public static Block getBlock(String block) {
		return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block));
	}

	public static ItemStack tryGetItemStack(String itemName) {
		Item item = tryGetItem(itemName);
		if (item != null)
			return new ItemStack(item);
		return ItemStack.EMPTY;
	}

	public static ItemStack parseStack(String str) {
		try {
			String[] parts = str.split(" ");
			int count = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
			ItemParser itemparser = (new ItemParser(new StringReader(parts[0]), false)).parse();
			return new ItemInput(itemparser.getItem(), itemparser.getNbt()).createItemStack(count, false);
		} catch (CommandSyntaxException e) {
			return ItemStack.EMPTY;
		}
	}
	//
	//

	public static float[] RGBtoHSB(int r, int g, int b) {
		float hue, saturation, brightness;
		float[] hsbvals = new float[3];
		int cmax = (r > g) ? r : g;
		if (b > cmax)
			cmax = b;
		int cmin = (r < g) ? r : g;
		if (b < cmin)
			cmin = b;

		brightness = ((float) cmax) / 255.0f;
		if (cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		else
			saturation = 0;
		if (saturation == 0)
			hue = 0;
		else {
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if (r == cmax)
				hue = bluec - greenc;
			else if (g == cmax)
				hue = 2.0f + redc - bluec;
			else
				hue = 4.0f + greenc - redc;
			hue = hue / 6.0f;
			if (hue < 0)
				hue = hue + 1.0f;
		}
		hsbvals[0] = hue;
		hsbvals[1] = saturation;
		hsbvals[2] = brightness;
		return hsbvals;
	}

	public static int[] HSBtoRGB(float hue, float saturation, float brightness) {
		int r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = (int) (brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h) {
			case 0:
				r = (int) (brightness * 255.0f + 0.5f);
				g = (int) (t * 255.0f + 0.5f);
				b = (int) (p * 255.0f + 0.5f);
				break;
			case 1:
				r = (int) (q * 255.0f + 0.5f);
				g = (int) (brightness * 255.0f + 0.5f);
				b = (int) (p * 255.0f + 0.5f);
				break;
			case 2:
				r = (int) (p * 255.0f + 0.5f);
				g = (int) (brightness * 255.0f + 0.5f);
				b = (int) (t * 255.0f + 0.5f);
				break;
			case 3:
				r = (int) (p * 255.0f + 0.5f);
				g = (int) (q * 255.0f + 0.5f);
				b = (int) (brightness * 255.0f + 0.5f);
				break;
			case 4:
				r = (int) (t * 255.0f + 0.5f);
				g = (int) (p * 255.0f + 0.5f);
				b = (int) (brightness * 255.0f + 0.5f);
				break;
			case 5:
				r = (int) (brightness * 255.0f + 0.5f);
				g = (int) (p * 255.0f + 0.5f);
				b = (int) (q * 255.0f + 0.5f);
				break;
			}
		}
		return new int[] { r, g, b };
//		return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
	}

	public static boolean callBooleanMethod(Object obj, String methodName, Class<?> clazz, Object[] args,
			Class<?>[] paramTypes) {

		try {
			Method method = clazz.getDeclaredMethod(methodName, paramTypes);
			method.setAccessible(true);
			Object ret = method.invoke(obj, args);
			if (ret instanceof Boolean)
				return (Boolean) ret;
			else
				System.err.println("The return was not a boolean from method " + methodName + " from object "
						+ obj.toString() + " of class " + obj.getClass().getName());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.println("Could not call boolean method " + methodName + " from object " + obj.toString()
					+ " of class " + obj.getClass().getName());
			e.printStackTrace();
		}

		return false;
	}

//	public static List<Item> getTagItems(String input) {
//		List<Item> items = UtilTags.getTagItems(input);
//		if (items != null)
//			return items.stream().toList();
//
//		List<Block> blocks = UtilTags.getTagBlocks(input);
//		if (blocks != null)
//			return blocks.stream().map(b -> b.asItem()).collect(toList());
//		return null;
//	}

	public static ItemStack stackListFind_ignoreNr(List<ItemStack> possibleInputs, ItemStack stack1) {
		for (ItemStack stack : possibleInputs) {
			if (ItemStack.isSameItemSameTags(stack, stack1))
				return stack;
		}
		return null;
	}

	public static ItemStack stackListFind_moreOrEqualNr(ItemStack stack1, List<ItemStack> possibleInputs) {
		for (ItemStack stack : possibleInputs) {
			if (ItemStack.isSameItemSameTags(stack, stack1) && stack.getCount() >= stack1.getCount())
				return stack;
		}
		return null;
	}

	public static String substring(String s, int i) {
		if (i < 0) {
			return s.substring(s.length() + i, s.length());
		}
		return s.substring(i);
	}

}
