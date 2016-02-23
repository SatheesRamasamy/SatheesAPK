package example.sofarmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter
	{
		int size;
		Activity act;
		View layout;
		TextView pagenumber;
		ImageView page_img;
		Button click;
		private final int[] Imageid;
	
		public ViewPagerAdapter(ScanBarQrCodeActivity pagerActivity,int[] Imag, int noofsize) 
			{
				// TODO Auto-generated constructor stub
				size = noofsize;
				act = pagerActivity;
				Imageid = Imag;
			}
	
		@Override
		public int getCount() 
			{
				// TODO Auto-generated method stub
				return size;
			}
	
		@Override
		public Object instantiateItem(View container, int position)
			{
				// TODO Auto-generated method stub
				LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = inflater.inflate(R.layout.view_pages, null);
				pagenumber = (TextView) layout.findViewById(R.id.pagenumber);
				
				page_img = (ImageView) layout.findViewById(R.id.image_pager);
				//page_img.setBackgroundResource(resid);
				page_img.setImageResource(Imageid[position]);
				System.out.println("Pos = "+position);
				//int pagenumberTxt=position ;
				
				String [] text = {"Scan Bar Code","Scan QR Code","Don't Scan Like This","Scan BarCode Like This","These are the right way to scan","Scan QRCode Like This"};
				
				pagenumber.setText(text[position]);
				
				
				((ViewPager) container).addView(layout, 0);
				return layout;
			}
	
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) 
			{
				((ViewPager) arg0).removeView((View) arg2);
			}
	
		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == ((View) arg1);
			}
	
		@Override
		public Parcelable saveState()
			{
				return null;
			}
	

}
