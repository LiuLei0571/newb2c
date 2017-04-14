package com.example.b2c.net.response;

import com.example.b2c.net.response.common.ImageItem;

import java.util.List;

/**
 * 相册对象
 *
 */
public class ImageBucket
{
	public int count = 0;
	public String bucketName;
	public List<ImageItem> imageList;
	public boolean selected = false;
}
