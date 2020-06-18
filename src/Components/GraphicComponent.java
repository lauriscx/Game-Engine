package Components;

import Components.RawComponentDAO;
import Mesh.Material;
import Mesh.Mesh;
import Mesh.Mesh;
import Render.RenderQueue;

/*This component is add to render list and is processed every frame.*/

public class GraphicComponent extends RawComponentDAO {
	
	//Shader ID in list.
	private int 		Shader;
	//Render ID in list.
	private int			Render;
	//Is active(if it's not active render skip this component).
	private boolean 	State;
	//It will be render in shadow map.
	private boolean 	CastSadow;
	//3D or 2D mesh witch is used to render shape.
	private Mesh 		Mesh;
	//Material witch is used to paint shapes.
	private Material 	Material;
	//private boolean 	Changed;
	
	//Init object data.
	public GraphicComponent() {
		this.Render 	= -1;
		this.Shader 	= -1;
		this.State 		= true;
		this.Mesh		= new Mesh();
		this.Material	= new Material();
		this.CastSadow	= true;
		//this.Changed 	= true;
		//Adding to render list for processing.
		RenderQueue.Add(this);
	}
	
	//Setters getters.
	public void 	SetShader	(int Shader		) {
		this.Shader = Shader;
	}
	public int		GetShader	(				) {
		return Shader;
	}
	public void 	SetRender	(int Render		) {
		this.Render = Render;
	}
	public int 		GetRender	(				) {
		return Render;
	}
	public Mesh 	Mesh		(				) {
		return this.Mesh;
	}
	public Material Material	(				) {
		return this.Material;
	}
	
	public void 	setMesh		(Mesh mesh		) {
		this.Mesh = mesh;
	}
	public void 	setMaterial	(Material mate	) {
		this.Material = mate;
	}
	
	//Overriding RawComponentDAO functions Enable and Disable.
	@Override
	public void 	Enable		(				) {
		State 	= true;
	}
	@Override
	public void 	Disable		(				) {
		State 	= false;
	}
	public boolean 	GetState	(				) {
		return State;
	}
	public void 	CastShadow	(boolean cast	) {
		this.CastSadow = cast;
	}
	public boolean 	CastShadow	(				) {
		return this.CastSadow;
	}
}