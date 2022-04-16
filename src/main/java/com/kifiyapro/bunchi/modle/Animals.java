package com.kifiyapro.bunchi.modle;

public class Animals {
    private Long id;
    private Photos[] photos;

    public Animals(Long id, Photos[] photos) {
        this.id = id;
        this.photos = photos;
    }

    public Animals() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public  Photos[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photos[] photos) {
        this.photos = photos;
    }
}
