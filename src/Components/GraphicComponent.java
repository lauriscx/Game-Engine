package Components;

import Components.RawComponentDAO;
import Mesh.Material;
import Mesh.Mesh;
import Mesh.Mesh;
import Render.RenderQueue;

public class GraphicComponent extends RawComponentDAO {
	
	private int 		Shader;
	private int			Render;
	private boolean 	State;
	private boolean 	CastSadow;
	private Mesh 		Mesh;
	private Material 	Material;
	//private boolean 	Changed;
	
	public GraphicComponent() {
		this.Render 	= -1;
		this.Shader 	= -1;
		this.State 		= true;
		this.Mesh		= new Mesh();
		this.Material	= new Material();
		this.CastSadow	= true;
		//this.Changed 	= true;
		RenderQueue.Add(this);
	}
	
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