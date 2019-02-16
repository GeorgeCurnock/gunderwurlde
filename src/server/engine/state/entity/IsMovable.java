package server.engine.state.entity;

public interface IsMovable {
    public abstract int getMoveSpeed();

    public abstract void setMoveSpeed(int moveSpeed);

    public abstract void setMoving(boolean moving);

    public abstract boolean isMoving();
}
