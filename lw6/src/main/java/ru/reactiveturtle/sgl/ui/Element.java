package ru.reactiveturtle.sgl.ui;

import org.joml.Rectanglei;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Texture;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

public class Element extends Model2d implements IElement {
    private Layout parent;

    private Mesh rectangle;
    private int vertexBufferId;

    private Color backgroundColor = new Color(0, 0, 0, 0);

    private boolean isNeedRepaintTexture = false;
    private Texture texture;

    private boolean isNeedUpdateSize = false;
    private final Vector2i size = new Vector2i(0);

    private AdaptType adaptType = AdaptType.NONE;
    private final Vector2i adaptedSize = new Vector2i(0);

    private final Margins margins = new Margins();
    private final Rectanglei paddings = new Rectanglei();

    public Element() {
        init();
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        if (isNeedRepaintTexture) {
            isNeedRepaintTexture = false;
            repaintTexture(getTexture());
        }
        drawAllMeshes(shaderProgram);
    }

    @Override
    public void dispose() {
        super.dispose();
        texture.dispose();
    }

    protected void setParent(Layout layout) {
        this.parent = layout;
        adaptSize();
        updatePosition();
    }

    protected void invalidateTexture() {
        isNeedRepaintTexture = true;
    }

    @Override
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setWidth(int width) {
        size.x = width;
        if (isNeedUpdateSize) {
            adaptSize();
            invalidateTexture();
        }
    }

    @Override
    public void setHeight(int height) {
        size.y = height;
        if (isNeedUpdateSize) {
            adaptSize();
            invalidateTexture();
        }
    }

    @Override
    public void setSize(int width, int height) {
        isNeedUpdateSize = false;
        setWidth(width);
        setHeight(height);
        adaptSize();
        invalidateTexture();
        isNeedUpdateSize = true;
    }

    @Override
    public void setSize(Vector2i size) {
        setSize(size.x, size.y);
    }

    @Override
    public int getWidth() {
        return size.x;
    }

    @Override
    public int getHeight() {
        return size.y;
    }

    @Override
    public Vector2i getSize() {
        return new Vector2i(getWidth(), getHeight());
    }

    @Override
    public void setAdaptType(AdaptType adaptType) {
        if (this.adaptType == adaptType) {
            return;
        }
        this.adaptType = adaptType;
        if (adaptType == null) {
            this.adaptType = AdaptType.NONE;
        }
        adaptSize();
    }

    @Override
    public AdaptType getAdaptType() {
        return adaptType;
    }

    protected void setAdaptedSize(int width, int height) {
        this.adaptedSize.set(width, height);
        updateSize();
        updatePosition();
    }

    @Override
    public float getAdaptedWidth() {
        return adaptedSize.x;
    }

    @Override
    public float getAdaptedHeight() {
        return adaptedSize.y;
    }

    @Override
    public Vector2i getAdaptedSize() {
        return new Vector2i(adaptedSize);
    }

    @Override
    public void setMarginLeft(int marginLeft) {
        this.margins.setMarginLeft(marginLeft);
        updatePosition();
    }

    @Override
    public void setMarginTop(int marginTop) {
        this.margins.setMarginTop(marginTop);
        updatePosition();
    }

    @Override
    public void setMarginRight(int marginRight) {
        this.margins.setMarginRight(marginRight);
        updatePosition();
    }

    @Override
    public void setMarginBottom(int marginBottom) {
        this.margins.setMarginBottom(marginBottom);
        updatePosition();
    }

    @Override
    public int getMarginLeft() {
        return margins.getMarginLeft();
    }

    @Override
    public int getMarginTop() {
        return margins.getMarginTop();
    }

    @Override
    public int getMarginRight() {
        return margins.getMarginRight();
    }

    @Override
    public int getMarginBottom() {
        return margins.getMarginBottom();
    }

    @Override
    public Margins getMargins() {
        return margins;
    }

    @Override
    public void setPaddingLeft(int paddingLeft) {

    }

    @Override
    public void setPaddingTop(int paddingTop) {

    }

    @Override
    public void setPaddingRight(int paddingRight) {

    }

    @Override
    public void setPaddingBottom(int paddingBottom) {

    }

    @Override
    public int getPaddingLeft() {
        return 0;
    }

    @Override
    public int getPaddingTop() {
        return 0;
    }

    @Override
    public int getPaddingRight() {
        return 0;
    }

    @Override
    public int getPaddingBottom() {
        return 0;
    }

    @Override
    public Paddings getPaddings() {
        return null;
    }

    protected void onAdaptNone() {
        adaptedSize.set(size);
    }

    protected void onAdaptParent() {
        if (parent != null) {
            adaptedSize.set(parent.getAdaptedSize());
        }
    }

    protected void onAdaptFit() {
        onAdaptNone();
    }

    protected void adaptSize() {
        switch (adaptType) {
            case NONE:
                onAdaptNone();
                break;
            case PARENT:
                onAdaptParent();
                break;
            case FIT:
                onAdaptFit();
                break;
        }
        updateSize();
        updatePosition();
    }

    protected void repaintTexture(Texture texture) {
        texture.set(1, 1, new float[]{
                backgroundColor.redFloat(),
                backgroundColor.greenFloat(),
                backgroundColor.blueFloat(),
                backgroundColor.alphaFloat()
        });
    }

    protected Texture getTexture() {
        return texture;
    }

    private void init() {
        int indexCount = 6;
        rectangle = createMesh(indexCount, (mesh, shaderProgram) -> {
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);

            GL20.glEnableVertexAttribArray(1);
            GL20.glActiveTexture(GL_TEXTURE0);
            GL20.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());

            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

            GL11.glBindTexture(GL_TEXTURE_2D, 0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        vertexBufferId = rectangle.createBuffer();

        updateSize();

        int indexBufferId = rectangle.createBuffer();

        rectangle.storeElementArrayBuffer(indexBufferId, new int[]{
                0, 1, 2,
                2, 0, 3
        });

        int texCoordsBufferId = rectangle.createBuffer();
        rectangle.storeArrayBuffer(texCoordsBufferId, new float[]{
                0f, 1f,
                0f, 0f,
                1f, 0f,
                1f, 1f
        }, 1, 2);

        texture = new Texture(1, 1, Texture.PixelFormat.RGBA);
    }

    private void updateSizeAndInvalidate() {
        updateSize();
        updatePosition();
        invalidateTexture();
    }

    private void updateSize() {
        float halfWidth = adaptedSize.x / 2f;
        float halfHeight = adaptedSize.y / 2f;
        rectangle.storeArrayBuffer(vertexBufferId, new float[]{
                -halfWidth, -halfHeight,
                -halfWidth, halfHeight,
                halfWidth, halfHeight,
                halfWidth, -halfHeight
        }, 0, 2);
    }

    private void updatePosition() {
        if (parent == null) {
            return;
        }
        float marginX = (margins.getMarginRight() - margins.getMarginLeft()) / 2f
                * Math.abs(Math.max(Math.signum(Math.min(
                        adaptedSize.x + margins.getMarginLeft() + margins.getMarginRight(),
                parent.getAdaptedWidth()) - parent.getAdaptedWidth()), 0));
        float x = -parent.getAdaptedWidth() / 2f + parent.getX() + getAdaptedWidth() / 2f
                + margins.getMarginLeft() - marginX;
        float marginY = (margins.getMarginBottom() - margins.getMarginTop()) / 2f
                * Math.abs(Math.max(Math.signum(Math.min(
                        adaptedSize.y + margins.getMarginBottom() + margins.getMarginTop(),
                parent.getAdaptedHeight()) - parent.getAdaptedHeight()), 0));
        float y = parent.getY() + parent.getAdaptedHeight() / 2f - (getAdaptedHeight() / 2f + margins.getMarginTop() - marginY);
        setPosition(x, y);
    }
}
