package moodss.bm.mixins.client.gl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = GlStateManager.class, remap = false)
public class GlStateManagerMixin {

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int glGetShaderi(int shader, int pname) {
        RenderSystem.assertOnRenderThread();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL20.nglGetShaderiv(shader, pname, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int glGetProgrami(int program, int pname) {
        RenderSystem.assertOnRenderThread();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL20.nglGetProgramiv(program, pname, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int _glGenBuffers() {
        RenderSystem.assertOnGameThreadOrInit();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL20.nglGenBuffers(1, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int _glGenVertexArrays() {
        RenderSystem.assertOnRenderThreadOrInit();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL30.nglGenVertexArrays(1, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }


    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int glGenFramebuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL30.nglGenFramebuffers(1, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int glGenRenderbuffers() {
        RenderSystem.assertOnRenderThreadOrInit();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL30.nglGenRenderbuffers(1, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int _getTexLevelParameter(int target, int level, int pname) {
        RenderSystem.assertInInitPhase();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL11.nglGetTexLevelParameteriv(target, level, pname, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int _genTexture() {
        RenderSystem.assertOnRenderThreadOrInit();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL11.nglGenTextures(1, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static int _getInteger(int pname) {
        RenderSystem.assertOnRenderThreadOrInit();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL11.nglGetIntegerv(pname, statusPointer);
            return MemoryUtil.memGetInt(statusPointer);
        }
    }
}
